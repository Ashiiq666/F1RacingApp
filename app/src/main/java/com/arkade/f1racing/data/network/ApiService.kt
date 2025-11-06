package com.arkade.f1racing.data.network


import com.arkade.f1racing.data.model.DriverResponse
import com.arkade.f1racing.data.model.RaceResponse
import com.arkade.f1racing.utils.Constants.BASE_URL
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class ApiService {
    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
                prettyPrint = true
            })
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.INFO
        }
    }

    suspend fun getDrivers(): Result<DriverResponse> {
        return try {
            val response = client.get(BASE_URL)
            Result.success(response.body())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getRaces(): Result<RaceResponse> {
        return try {
            val response = client.get(BASE_URL)
            Result.success(response.body())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun close() {
        client.close()
    }
}