package com.mentalmachines.travel_app.ui.Trips

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mentalmachines.travel_app.database.Resource
import com.mentalmachines.travel_app.domain.DaySchedule
import com.mentalmachines.travel_app.domain.Trip
import com.mentalmachines.travel_app.repository.TripRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TripDetailUiState(
    val trip: Trip? = null,
    val dailySchedules: List<DaySchedule> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)

@HiltViewModel
class TripDetailViewModel @Inject constructor(
    private val tripRepository: TripRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val tripId: String = savedStateHandle["tripId"] ?: ""

    var uiState by mutableStateOf(TripDetailUiState())
        private set

    init {
        loadTripDetails()
        loadTripSchedules()
    }

    private fun loadTripDetails() {
        if (tripId.isBlank()) return
        viewModelScope.launch {
            tripRepository.getTripById(tripId).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        uiState = uiState.copy(trip = result.data, isLoading = false)
                    }
                    is Resource.Error -> {
                        uiState = uiState.copy(errorMessage = result.message, isLoading = false)
                    }
                    Resource.Loading -> {
                        uiState = uiState.copy(isLoading = true)
                    }
                }
            }
        }
    }

    private fun loadTripSchedules() {
        if (tripId.isBlank()) return
        viewModelScope.launch {
            tripRepository.getTripSchedules(tripId).collect { schedules ->
                uiState = uiState.copy(dailySchedules = schedules)
            }
        }
    }
}