package com.mentalmachines.travel_app.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mentalmachines.travel_app.ui.theme.TravelAppTheme

// ============================================================================
// DATA MODEL
// ============================================================================
data class TripUiModel(
    val id: String,
    val destination: String,
    val dates: String,
    val imageUrl: String
)

// ============================================================================
// ATOMS
// ============================================================================

@Composable
fun LocationImage(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier
) {
    val gradientColors = listOf(
        MaterialTheme.colorScheme.tertiary,
        MaterialTheme.colorScheme.primary
    )
    Box(
        modifier = modifier.background(brush = Brush.linearGradient(colors = gradientColors))
    )
}

@Composable
fun LocationTitleText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = Color.White,
        style = MaterialTheme.typography.titleLarge,
        modifier = modifier
    )
}

@Composable
fun LocationDateText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = Color.White.copy(alpha = 0.85f),
        style = MaterialTheme.typography.bodyMedium,
        modifier = modifier
    )
}

// ============================================================================
// MOLECULES
// ============================================================================

@Composable
fun LocationDetailsOverlay(
    destination: String,
    dates: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.75f))
                )
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        LocationTitleText(text = destination)
        LocationDateText(text = dates)
    }
}

// ============================================================================
// ORGANISMS
// ============================================================================

@Composable
fun TripCard(
    trip: TripUiModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(280.dp)
            .height(180.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            LocationImage(
                imageUrl = trip.imageUrl,
                contentDescription = trip.destination,
                modifier = Modifier.fillMaxSize()
            )
            LocationDetailsOverlay(
                destination = trip.destination,
                dates = trip.dates,
                modifier = Modifier.align(Alignment.BottomStart)
            )
        }
    }
}

@Composable
fun HorizontalTripRow(
    trips: List<TripUiModel>,
    onTripClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(trips, key = { it.id }) { trip ->
            TripCard(trip = trip, onClick = { onTripClick(trip.id) })
        }
    }
}

// ============================================================================
// TEMPLATES
// ============================================================================

@Composable
fun HomeContentTemplate(
    featuredTrips: List<TripUiModel>,
    onTripClick: (String) -> Unit,
    onPackListClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 24.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Explore Destinations",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
            Button(onClick = onPackListClick) {
                Text("Pack List")
            }
        }
        HorizontalTripRow(trips = featuredTrips, onTripClick = onTripClick)
    }
}

// ============================================================================
// PAGES
// ============================================================================

@Composable
fun HomeScreen(navController: NavController) {
    val sampleTrips = listOf(
        TripUiModel("1", "Paris, France", "Oct 12 - Oct 19", ""),
        TripUiModel("2", "Tokyo, Japan", "Nov 02 - Nov 12", ""),
        TripUiModel("3", "Rome, Italy", "Dec 05 - Dec 10", ""),
        TripUiModel("4", "New York, USA", "Jan 15 - Jan 18", "")
    )

    HomeContentTemplate(
        featuredTrips = sampleTrips,
        onTripClick = { tripId ->
            navController.navigate("details_screen/$tripId")
        },
        onPackListClick = {
            navController.navigate("pack_list_screen")
        }
    )
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    TravelAppTheme {
        HomeScreen(rememberNavController())
    }
}
