package com.arkade.f1racing.data.network


import com.arkade.f1racing.data.model.Driver
import com.arkade.f1racing.data.model.DriverResponse
import com.arkade.f1racing.data.model.Race
import com.arkade.f1racing.data.model.ScheduleResponse
import com.arkade.f1racing.utils.Constants.BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


class ApiService {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }

        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.BODY
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 15000
            connectTimeoutMillis = 15000
            socketTimeoutMillis = 15000
        }

        defaultRequest {
            url(BASE_URL)
        }
    }

    suspend fun getDrivers(): Result<DriverResponse> {
        val DRIVER_END_URL = "e8616da8-220c-4aab-a670-ab2d43224ecb"
        return try {
            val response = client.get(DRIVER_END_URL)
            Result.success(response.body<DriverResponse>())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getLeadingDriver(): Result<Driver?> {
        return try {
            val response = getDrivers()
            response.map { driversResponse ->
                driversResponse.drivers.find { it.position == 1 }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    suspend fun getRaceSchedule(): Result<ScheduleResponse> {
        val RACE_END_URL = "9086a3f1-f02b-4d24-8dd3-b63582f45e67"
        return try {
            val response = client.get(RACE_END_URL)
            Result.success(response.body<ScheduleResponse>())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUpcomingRace(): Result<Race?> {
        return try {
            val response = getRaceSchedule()
            response.map { scheduleResponse ->
                val now = System.currentTimeMillis() / 1000 // Current time in seconds
                scheduleResponse.schedule
                    .filter { it.raceState == "upcoming" || it.raceStartTime > now }
                    .minByOrNull { it.raceStartTime }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    fun close() {
        client.close()
    }
}