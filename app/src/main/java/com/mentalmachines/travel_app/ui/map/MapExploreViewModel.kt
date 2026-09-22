package com.mentalmachines.travel_app.ui.map

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class MapExploreUiState(
    val places: List<InterestPlace> = emptyList(),
    val selectedPlace: InterestPlace? = null
)

@HiltViewModel
class MapExploreViewModel @Inject constructor() : ViewModel() {

    var uiState by mutableStateOf(MapExploreUiState())
        private set

    init {
        val samplePlaces = listOf(
            InterestPlace("1", "Le Bistrot Gourmand", "Traditional French", 4.8, "€€€", listOf("Recommended", "Romantic", "Outdoor Seating"), 60, 120),
            InterestPlace("2", "Sushi Kyoto Star", "Authentic Japanese", 4.9, "€€€€", listOf("Top Rated", "Fresh Fish", "Chef's Menu"), 180, 260),
            InterestPlace("3", "Pizzeria Roma Bella", "Classic Italian Pizza", 4.6, "€€", listOf("Family Friendly", "Wood Oven", "Fast Service"), 120, 420)
        )
        uiState = MapExploreUiState(
            places = samplePlaces,
            selectedPlace = samplePlaces.firstOrNull()
        )
    }

    fun selectPlace(place: InterestPlace) {
        uiState = uiState.copy(selectedPlace = place)
    }
}
