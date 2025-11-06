package com.arkade.f1racing.presentation.details

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arkade.f1racing.data.model.Race
import com.arkade.f1racing.data.model.Session
import com.arkade.f1racing.data.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DetailsUiState(
    val isLoading: Boolean = true,
    val race: Race? = null,
    val error: String? = null,
    val nextSession: Session? = null,

    )

@RequiresApi(Build.VERSION_CODES.O)
class DetailsViewModel(private val repository: Repository) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailsUiState())
    val uiState: StateFlow<DetailsUiState> = _uiState.asStateFlow()

    init {
        loadRaceDetails()
    }

    fun loadRaceDetails() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            try {
                val raceResult = repository.getUpcomingRace()
                val race = raceResult.getOrNull()

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        race = race
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