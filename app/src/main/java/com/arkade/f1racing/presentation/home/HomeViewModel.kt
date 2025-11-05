package com.arkade.f1racing.presentation.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arkade.f1racing.data.model.Driver
import com.arkade.f1racing.data.model.Race
import com.arkade.f1racing.data.model.Session
import com.arkade.f1racing.data.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

data class HomeUiState(
    val isLoading: Boolean = true,
    val topDriver: Driver? = null,
    val upcomingRace: Race? = null,
    val nextSession: Session? = null,
    val nextSessionLocalTime: String? = null,
    val error: String? = null
)


@RequiresApi(Build.VERSION_CODES.O)
class HomeViewModel(private val repository: Repository) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadRaceData()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadRaceData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            try {
                // Fetch top driver
                val driverResult = repository.getTopDriver()
                val driver = driverResult.getOrNull()

                // Fetch upcoming race
                val raceResult = repository.getUpcomingRace()
                val race = raceResult.getOrNull()

                // Find next session
                val (nextSession, localTime) = race?.let { findNextSession(it) } ?: (null to null)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        topDriver = driver,
                        upcomingRace = race,
                        nextSession = nextSession,
                        nextSessionLocalTime = localTime
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "An error occurred"
                    )
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun findNextSession(race: Race): Pair<Session?, String?> {
        val now = Instant.now()
        val nextSession = race.sessions
            .filter {
                try {
                    val sessionEnd = ZonedDateTime.parse(
                        it.endTime,
                        DateTimeFormatter.ISO_DATE_TIME
                    ).toInstant()
                    sessionEnd.isAfter(now)
                } catch (e: Exception) {
                    false
                }
            }
            .minByOrNull {
                try {
                    ZonedDateTime.parse(
                        it.startTime,
                        DateTimeFormatter.ISO_DATE_TIME
                    ).toInstant()
                } catch (e: Exception) {
                    Instant.MAX
                }
            }

        val localTime = nextSession?.let {
            try {
                val zonedDateTime = ZonedDateTime.parse(
                    it.startTime,
                    DateTimeFormatter.ISO_DATE_TIME
                )
                val localZonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.systemDefault())
                localZonedDateTime.format(DateTimeFormatter.ofPattern("MMM dd, HH:mm"))
            } catch (e: Exception) {
                null
            }
        }

        return nextSession to localTime
    }
}