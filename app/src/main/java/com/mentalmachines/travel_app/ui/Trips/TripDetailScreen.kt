package com.mentalmachines.travel_app.ui.Trips

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
import com.mentalmachines.travel_app.ui.components.TravelTopAppBar
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

data class DaySchedule(
    val dayNumber: Int,
    val dateText: String,
    val items: List<ItineraryItem>
)

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

            DailyScheduleList(
                dailySchedules = dailySchedules,
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
    onTransitClick: () -> Unit,
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

    val dailySchedules = when(tripId) {
        "1" -> listOf(
            DaySchedule(
                dayNumber = 1,
                dateText = "Oct 12 • Arrival & Eiffel Tower",
                items = listOf(
                    ItineraryItem("1", "Morning Flight & Hotel Check-in", "08:00 AM - 01:00 PM"),
                    ItineraryItem("2", "Eiffel Tower Guided Walk & Photo Stop", "03:00 PM - 06:00 PM"),
                    ItineraryItem("3", "Welcome Dinner at Seine River Bistro", "07:30 PM - 09:30 PM")
                )
            ),
            DaySchedule(
                dayNumber = 2,
                dateText = "Oct 13 • Art & Culture",
                items = listOf(
                    ItineraryItem("4", "Louvre Museum Guided Tour", "09:30 AM - 01:00 PM"),
                    ItineraryItem("5", "Montmartre & Sacré-Cœur Stroll", "02:30 PM - 05:30 PM")
                )
            ),
            DaySchedule(
                dayNumber = 3,
                dateText = "Oct 14 • Day Trip & Jazz Night",
                items = listOf(
                    ItineraryItem("6", "Palace of Versailles Excursion", "08:30 AM - 04:00 PM"),
                    ItineraryItem("7", "Evening Jazz Club in Le Marais", "08:00 PM - 10:30 PM")
                )
            )
        )
        "2" -> listOf(
            DaySchedule(
                dayNumber = 1,
                dateText = "Nov 02 • Shinjuku Arrival",
                items = listOf(
                    ItineraryItem("8", "Arrival at Narita & Shinjuku Check-in", "10:00 AM - 02:00 PM"),
                    ItineraryItem("9", "Omoide Yokocho Evening Food Tour", "06:00 PM - 09:00 PM")
                )
            ),
            DaySchedule(
                dayNumber = 2,
                dateText = "Nov 03 • Historic Asakusa & Akihabara",
                items = listOf(
                    ItineraryItem("10", "Asakusa Senso-ji Temple & Nakamise St", "09:00 AM - 12:30 PM"),
                    ItineraryItem("11", "Akihabara Tech & Manga Exploration", "02:00 PM - 06:00 PM")
                )
            ),
            DaySchedule(
                dayNumber = 3,
                dateText = "Nov 04 • Harajuku & Shibuya",
                items = listOf(
                    ItineraryItem("12", "Meiji Shrine & Harajuku Takeshita St", "10:00 AM - 01:30 PM"),
                    ItineraryItem("13", "Shibuya Crossing & Rooftop View", "04:00 PM - 07:30 PM")
                )
            )
        )
        "3" -> listOf(
            DaySchedule(
                dayNumber = 1,
                dateText = "Dec 05 • Historic Center Arrival",
                items = listOf(
                    ItineraryItem("14", "Arrival & Trastevere Walk", "11:00 AM - 03:00 PM"),
                    ItineraryItem("15", "Traditional Roman Pasta Dinner", "07:00 PM - 09:00 PM")
                )
            ),
            DaySchedule(
                dayNumber = 2,
                dateText = "Dec 06 • Ancient Wonders",
                items = listOf(
                    ItineraryItem("16", "Colosseum & Roman Forum Tour", "09:00 AM - 01:00 PM"),
                    ItineraryItem("17", "Trevi Fountain & Pantheon Stroll", "03:00 PM - 06:00 PM")
                )
            )
        )
        else -> listOf(
            DaySchedule(
                dayNumber = 1,
                dateText = "Jan 15 • Manhattan Arrival",
                items = listOf(
                    ItineraryItem("18", "Hotel Check-in & Times Square Walk", "01:00 PM - 04:00 PM"),
                    ItineraryItem("19", "Broadway Evening Show", "07:00 PM - 10:00 PM")
                )
            ),
            DaySchedule(
                dayNumber = 2,
                dateText = "Jan 16 • Central Park & Museums",
                items = listOf(
                    ItineraryItem("20", "Central Park Walk & MET Museum", "09:30 AM - 02:00 PM"),
                    ItineraryItem("21", "Empire State Building Night View", "06:30 PM - 08:30 PM")
                )
            )
        )
    }

    TripDetailContentTemplate(
        destinationName = destinationName,
        datesText = datesText,
        dailySchedules = dailySchedules,
        onBackClick = onBackClick,
        onExploreMapClick = onExploreMapClick,
        onTransitClick = onTransitClick
    )
}
