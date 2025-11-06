package com.arkade.f1racing.data.network


import com.arkade.f1racing.data.model.Driver
import com.arkade.f1racing.data.model.DriverResponse
import com.arkade.f1racing.data.model.RaceResponse
import com.arkade.f1racing.utils.Constants.DRIVERS_URL
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import io.ktor.client.engine.cio.*


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
            url("https://mocki.io/v1/")
        }
    }

    suspend fun getDrivers(): Result<DriverResponse> {
        return try {
            val response = client.get("e8616da8-220c-4aab-a670-ab2d43224ecb")
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



    suspend fun getRaces(): Result<RaceResponse> {
        return try {
            val response = client.get(DRIVERS_URL)
            Result.success(response.body())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun close() {
        client.close()
    }
}