package com.mentalmachines.travel_app.ui.map

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mentalmachines.travel_app.domain.InterestPlace
import com.mentalmachines.travel_app.repository.TripRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MapExploreUiState(
    val places: List<InterestPlace> = emptyList(),
    val selectedPlace: InterestPlace? = null,
    val isLoading: Boolean = true
)

@HiltViewModel
class MapExploreViewModel @Inject constructor(
    private val tripRepository: TripRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val tripId: String = savedStateHandle["tripId"] ?: ""

    var uiState by mutableStateOf(MapExploreUiState())
        private set

    init {
        loadInterestPlaces()
    }

    private fun loadInterestPlaces() {
        if (tripId.isBlank()) return
        viewModelScope.launch {
            tripRepository.getInterestPlaces(tripId).collect { places ->
                uiState = uiState.copy(
                    places = places,
                    selectedPlace = places.firstOrNull(),
                    isLoading = false
                )
            }
        }
    }

    fun selectPlace(place: InterestPlace) {
        uiState = uiState.copy(selectedPlace = place)
    }
}
