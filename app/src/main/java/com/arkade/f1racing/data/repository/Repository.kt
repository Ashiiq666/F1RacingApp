package com.arkade.f1racing.data.repository


import android.os.Build
import androidx.annotation.RequiresApi
import com.arkade.f1racing.data.model.Driver
import com.arkade.f1racing.data.model.Race
import com.arkade.f1racing.data.model.Session
import com.arkade.f1racing.data.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.Instant
import java.time.ZoneId
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


    suspend fun getAllRaces(): Result<List<Race>> = withContext(Dispatchers.IO) {
        apiService.getRaceSchedule().map { it.schedule }
    }

    suspend fun getUpcomingRace(): Result<Race?> = withContext(Dispatchers.IO) {
        apiService.getUpcomingRace()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getNextSession(race: Race): Result<Pair<Session?, String?>> = withContext(Dispatchers.IO) {
        try {
            val now = Instant.now()
            val nextSession = race.sessions
                .filter { session ->
                    try {
                        val sessionEnd = Instant.ofEpochSecond(session.endTime)
                        sessionEnd.isAfter(now)
                    } catch (e: Exception) {
                        false
                    }
                }
                .minByOrNull { session ->
                    try {
                        session.startTime
                    } catch (e: Exception) {
                        Long.MAX_VALUE
                    }
                }

            val localTime = nextSession?.let { session ->
                try {
                    val zonedDateTime = Instant.ofEpochSecond(session.startTime)
                        .atZone(ZoneId.systemDefault())
                    zonedDateTime.format(DateTimeFormatter.ofPattern("dd EEEE"))
                } catch (e: Exception) {
                    null
                }
            }

            Result.success(nextSession to localTime)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getNextSessionTime(race: Race): Result<String?> = withContext(Dispatchers.IO) {
        try {
            val now = Instant.now()
            val nextSession = race.sessions
                .filter { session ->
                    try {
                        val sessionEnd = Instant.ofEpochSecond(session.endTime)
                        sessionEnd.isAfter(now)
                    } catch (e: Exception) {
                        false
                    }
                }
                .minByOrNull { it.startTime }

            val formattedTime = nextSession?.let { session ->
                try {
                    val zonedDateTime = Instant.ofEpochSecond(session.startTime)
                        .atZone(ZoneId.systemDefault())
                    zonedDateTime.format(DateTimeFormatter.ofPattern("h.mm"))
                } catch (e: Exception) {
                    null
                }
            }

            Result.success(formattedTime)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getSessionAmPm(race: Race): Result<String?> = withContext(Dispatchers.IO) {
        try {
            val now = Instant.now()
            val nextSession = race.sessions
                .filter { session ->
                    try {
                        val sessionEnd = Instant.ofEpochSecond(session.endTime)
                        sessionEnd.isAfter(now)
                    } catch (e: Exception) {
                        false
                    }
                }
                .minByOrNull { it.startTime }

            val amPm = nextSession?.let { session ->
                try {
                    val zonedDateTime = Instant.ofEpochSecond(session.startTime)
                        .atZone(ZoneId.systemDefault())
                    zonedDateTime.format(DateTimeFormatter.ofPattern("a"))
                } catch (e: Exception) {
                    null
                }
            }

            Result.success(amPm)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun cleanup() {
        apiService.close()
    }


}