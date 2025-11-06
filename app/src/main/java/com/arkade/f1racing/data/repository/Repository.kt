package com.arkade.f1racing.data.repository


import android.os.Build
import androidx.annotation.RequiresApi
import com.arkade.f1racing.data.model.Driver
import com.arkade.f1racing.data.model.Race
import com.arkade.f1racing.data.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.Instant
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class Repository(private val apiService: ApiService) {

    suspend fun getAllDrivers(): Result<List<Driver>> = withContext(Dispatchers.IO) {
        apiService.getDrivers().map { it.drivers }
    }

    suspend fun getLeadingDriver(): Result<Driver?> = withContext(Dispatchers.IO) {
        apiService.getLeadingDriver()
    }

    suspend fun getDriversByTeam(teamId: String): Result<List<Driver>> = withContext(Dispatchers.IO) {
        apiService.getDrivers().map { response ->
            response.drivers.filter { it.teamId == teamId }
        }
    }


    suspend fun getDriverByPosition(position: Int): Result<Driver?> = withContext(Dispatchers.IO) {
        apiService.getDrivers().map { response ->
            response.drivers.find { it.position == position }
        }
    }

    suspend fun getTopDrivers(limit: Int = 10): Result<List<Driver>> = withContext(Dispatchers.IO) {
        apiService.getDrivers().map { response ->
            response.drivers.sortedBy { it.position }.take(limit)
        }
    }

    fun cleanup() {
        apiService.close()
    }


}