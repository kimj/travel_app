package com.mentalmachines.travel_app.ui.Trips

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mentalmachines.travel_app.domain.Trip
import com.mentalmachines.travel_app.repository.TripRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DraftEditUiState(
    val draftId: String = "",
    val destination: String = "",
    val duration: String = "",
    val isConverting: Boolean = false,
    val isConverted: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class TripDraftEditViewModel @Inject constructor(
    private val tripRepository: TripRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var uiState by mutableStateOf(DraftEditUiState())
        private set

    init {
        val draftId: String = savedStateHandle["draftId"] ?: ""
        loadDraftData(draftId)
    }

    private fun loadDraftData(draftId: String) {
        val (destination, duration) = when (draftId) {
            "5" -> "Barcelona, Spain" to "6 Days"
            "6" -> "Kyoto, Japan" to "4 Days"
            "7" -> "Reykjavik, Iceland" to "5 Days"
            else -> "New Destination" to "3 Days"
        }

        uiState = uiState.copy(
            draftId = draftId,
            destination = destination,
            duration = duration
        )
    }

    fun updateDestination(newDestination: String) {
        uiState = uiState.copy(destination = newDestination)
    }

    fun updateDuration(newDuration: String) {
        uiState = uiState.copy(duration = newDuration)
    }

    fun convertToActualTrip(onSuccess: (String) -> Unit) {
        if (uiState.destination.isBlank()) {
            uiState = uiState.copy(errorMessage = "Destination cannot be empty")
            return
        }

        viewModelScope.launch {
            uiState = uiState.copy(isConverting = true, errorMessage = null)
            val newTrip = Trip(
                id = uiState.draftId.ifEmpty { System.currentTimeMillis().toString() },
                destination = uiState.destination,
                duration = uiState.duration
            )

            val result = tripRepository.addTrip(newTrip)
            if (result.isSuccess) {
                uiState = uiState.copy(isConverting = false, isConverted = true)
                onSuccess(newTrip.id)
            } else {
                uiState = uiState.copy(
                    isConverting = false,
                    errorMessage = result.exceptionOrNull()?.message ?: "Failed to save trip"
                )
            }
        }
    }
}
