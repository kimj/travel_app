package com.mentalmachines.travel_app.ui.Trips

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mentalmachines.travel_app.ui.home.LocationImage
import com.mentalmachines.travel_app.ui.home.LocationDetailsOverlay

// ============================================================================
// DATA MODELS
// ============================================================================
data class ItineraryItem(
    val id: String,
    val stopLocation: String,
    val timeRange: String
)

// ============================================================================
// ATOMS
// ============================================================================

@Composable
fun ItineraryTitleText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = modifier
    )
}

@Composable
fun ItineraryTimeText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier
    )
}

@Composable
fun SectionHeader(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = modifier
    )
}

// ============================================================================
// MOLECULES
// ============================================================================

@Composable
fun ItineraryItemContent(
    item: ItineraryItem,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        ItineraryTitleText(text = item.stopLocation)
        ItineraryTimeText(text = item.timeRange)
    }
}

// ============================================================================
// ORGANISMS
// ============================================================================

@Composable
fun ItineraryCard(
    item: ItineraryItem,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        ItineraryItemContent(
            item = item,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun ItineraryList(
    itineraryList: List<ItineraryItem>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(itineraryList, key = { it.id }) { item ->
            ItineraryCard(item = item)
        }
    }
}

// ============================================================================
// TEMPLATES
// ============================================================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripDetailContentTemplate(
    destinationName: String,
    datesText: String,
    itineraryList: List<ItineraryItem>,
    onBackClick: () -> Unit,
    onExploreMapClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Trip Itinerary", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    Button(
                        onClick = onExploreMapClick,
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text("Explore Map")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            ) {
                LocationImage(
                    imageUrl = "",
                    contentDescription = destinationName,
                    modifier = Modifier.fillMaxSize()
                )
                LocationDetailsOverlay(
                    destination = destinationName,
                    dates = datesText,
                    modifier = Modifier.align(Alignment.BottomStart)
                )
            }

            SectionHeader(
                text = "Daily Schedule",
                modifier = Modifier.padding(start = 16.dp, top = 20.dp, end = 16.dp, bottom = 8.dp)
            )

            ItineraryList(
                itineraryList = itineraryList,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

// ============================================================================
// PAGES
// ============================================================================

@Composable
fun TripDetailScreen(
    tripId: String,
    onExploreMapClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val destinationName = when(tripId) {
        "1" -> "Paris, France"
        "2" -> "Tokyo, Japan"
        "3" -> "Rome, Italy"
        else -> "New York, USA"
    }
    val datesText = when(tripId) {
        "1" -> "Oct 12 - Oct 19"
        "2" -> "Nov 02 - Nov 12"
        "3" -> "Dec 05 - Dec 10"
        else -> "Jan 15 - Jan 18"
    }

    val itineraryList = listOf(
        ItineraryItem("1", "Morning Flight & Hotel Check-in", "08:00 AM - 01:00 PM"),
        ItineraryItem("2", "Local City Center Guided Walk", "03:00 PM - 06:00 PM"),
        ItineraryItem("3", "Welcome Dinner at Historic Bistro", "07:30 PM - 09:30 PM"),
        ItineraryItem("4", "Museum & Landmark Sightseeing Tour", "09:00 AM - 02:00 PM")
    )

    TripDetailContentTemplate(
        destinationName = destinationName,
        datesText = datesText,
        itineraryList = itineraryList,
        onBackClick = onBackClick,
        onExploreMapClick = onExploreMapClick
    )
}
