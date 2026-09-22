package com.mentalmachines.travel_app.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.mentalmachines.travel_app.ui.details.DetailsViewModel
import javax.inject.Inject
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mentalmachines.travel_app.domain.Trip


@Composable
fun HomeScreen() {
    val viewModel = hiltViewModel<HomeScreenViewModel>()

    viewModel.uiState.copy()
    val sampleTrips = listOf(
        Trip("1", "Paris, France", "7 days"),
        Trip("2", "Tokyo, Japan", "10 days"),
        Trip("3", "Rome, Italy", "5 days"),
        Trip("4", "New York, USA", "3 days")
    )
    MaterialTheme { // Ensure a MaterialTheme is applied for Card styling
        TripsList(trips = sampleTrips)
    }

    //TripsList()
}


@Composable
fun TripsList(trips: List<Trip>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp) // Adds space between items
    ) {
        items(trips, key = { trip -> trip.id }) { trip ->
            TripItem(trip = trip)
        }
    }
}

@Composable
fun TripItem(trip: Trip, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Text(
            text = "Destination: ${trip.destination}",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = "Duration: ${trip.duration}",
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            style = MaterialTheme.typography.bodyMedium
        )
        // Add more Text or other composables here to display other trip details
    }
}

enum class TravelCardType {
    TRANSIT, LODGING, POINT_OF_INTEREST, RESTAURANT
}
