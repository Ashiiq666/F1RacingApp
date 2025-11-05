package com.arkade.f1racing.data.repository


import android.os.Build
import androidx.annotation.RequiresApi
import com.arkade.f1racing.data.model.Driver
import com.arkade.f1racing.data.model.Race
import com.arkade.f1racing.data.network.ApiService
import java.time.Instant
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class Repository(private val apiService: ApiService) {

    suspend fun getTopDriver(): Result<Driver?> {
        return try {
            val result = apiService.getDrivers()
            result.map { response ->
                response.drivers.find { it.position == 1 }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getUpcomingRace(): Result<Race?> {
        return try {
            val result = apiService.getRaces()
            result.map { response ->
                val now = Instant.now()
                response.races
                    .filter {
                        try {
                            val raceEnd = ZonedDateTime.parse(
                                it.raceEndTime,
                                DateTimeFormatter.ISO_DATE_TIME
                            ).toInstant()
                            raceEnd.isAfter(now)
                        } catch (e: Exception) {
                            false
                        }
                    }
                    .minByOrNull {
                        try {
                            ZonedDateTime.parse(
                                it.raceStartTime,
                                DateTimeFormatter.ISO_DATE_TIME
                            ).toInstant()
                        } catch (e: Exception) {
                            Instant.MAX
                        }
                    }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}