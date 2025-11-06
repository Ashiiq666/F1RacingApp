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
    val nextSessionDate: String? = null,
    val nextSessionTime: String? = null,
    val nextSessionAmPm: String? = null,
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

                val driverResult = repository.getLeadingDriver()
                val driver = driverResult.getOrNull()

                val raceResult = repository.getUpcomingRace()
                val race = raceResult.getOrNull()

                val (nextSession, sessionDate) = race?.let {
                    repository.getNextSession(it).getOrNull()
                } ?: (null to null)

                // Get session time
                val sessionTime = race?.let {
                    repository.getNextSessionTime(it).getOrNull()
                }


                val sessionAmPm = race?.let {
                    repository.getSessionAmPm(it).getOrNull()
                }
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        topDriver = driver,
                        upcomingRace = race,
                        nextSession = nextSession,
                        nextSessionDate = sessionDate,
                        nextSessionTime = sessionTime,
                        nextSessionAmPm = sessionAmPm
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

}