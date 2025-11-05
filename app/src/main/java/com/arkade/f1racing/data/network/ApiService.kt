package com.arkade.f1racing.data.network


import com.arkade.f1racing.data.model.DriverResponse
import com.arkade.f1racing.data.model.RaceResponse
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
            val response = client.get("https://mocki.io/v1/e8616da8-220c-4aab-a670-ab2d43224ecb")
            Result.success(response.body())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getRaces(): Result<RaceResponse> {
        return try {
            val response = client.get("https://mocki.io/v1/9086a3f1-f02b-4d24-8dd3-b63582f45e67")
            Result.success(response.body())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun close() {
        client.close()
    }
}