package com.mentalmachines.travel_app.ui.trips

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mentalmachines.travel_app.domain.DaySchedule
import com.mentalmachines.travel_app.domain.ItineraryItem
import com.mentalmachines.travel_app.ui.components.TravelTopAppBar
import com.mentalmachines.travel_app.ui.home.LocationImage
import com.mentalmachines.travel_app.ui.home.LocationDetailsOverlay

// ============================================================================
// ATOMS
// ============================================================================

@Composable
fun DayHeaderTitle(
    dayNumber: Int,
    dateText: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.primaryContainer
        ) {
            Text(
                text = "Day $dayNumber",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
            )
        }
        Text(
            text = dateText,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

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
fun DayScheduleSection(
    daySchedule: DaySchedule,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        DayHeaderTitle(
            dayNumber = daySchedule.dayNumber,
            dateText = daySchedule.dateText
        )
        daySchedule.items.forEach { item ->
            ItineraryCard(item = item)
        }
    }
}

@Composable
fun DailyScheduleList(
    dailySchedules: List<DaySchedule>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(dailySchedules, key = { it.dayNumber }) { daySchedule ->
            DayScheduleSection(daySchedule = daySchedule)
        }
    }
}

// ============================================================================
// TEMPLATES
// ============================================================================

@Composable
fun TripDetailContentTemplate(
    destinationName: String,
    datesText: String,
    dailySchedules: List<DaySchedule>,
    isLoading: Boolean,
    onBackClick: () -> Unit,
    onExploreMapClick: () -> Unit,
    onTransitClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TravelTopAppBar(
                title = "Trip Itinerary",
                onBackClick = onBackClick,
                actions = {
                    TextButton(onClick = onExploreMapClick) {
                        Text("Map", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                    }
                    TextButton(onClick = onTransitClick) {
                        Text("Transit", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                    }
                }
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

            Text(
                text = "Daily Schedule",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 4.dp)
            )

            if (isLoading) {
                Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                DailyScheduleList(
                    dailySchedules = dailySchedules,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

// ============================================================================
// PAGES
// ============================================================================

@Composable
fun TripDetailScreen(
    onExploreMapClick: () -> Unit,
    onTransitClick: () -> Unit,
    onBackClick: () -> Unit,
    viewModel: TripDetailViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState

    val destinationName = uiState.trip?.destination ?: "Loading..."
    val datesText = uiState.trip?.duration ?: ""

    TripDetailContentTemplate(
        destinationName = destinationName,
        datesText = datesText,
        dailySchedules = uiState.dailySchedules,
        isLoading = uiState.isLoading,
        onBackClick = onBackClick,
        onExploreMapClick = onExploreMapClick,
        onTransitClick = onTransitClick
    )
}
