package com.mentalmachines.travel_app.ui.transit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mentalmachines.travel_app.domain.TransitStop
import com.mentalmachines.travel_app.repository.TripRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TransitUiState(
    val transitStops: List<TransitStop> = emptyList(),
    val isLoading: Boolean = true
)

@HiltViewModel
class TransitViewModel @Inject constructor(
    private val tripRepository: TripRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val tripId: String = savedStateHandle["tripId"] ?: ""

    var uiState by mutableStateOf(TransitUiState())
        private set

    init {
        loadTransitStops()
    }

    private fun loadTransitStops() {
        if (tripId.isBlank()) return
        viewModelScope.launch {
            tripRepository.getTransitStops(tripId).collect { stops ->
                uiState = uiState.copy(
                    transitStops = stops,
                    isLoading = false
                )
            }
        }
    }
}
