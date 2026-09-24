package com.mentalmachines.travel_app.ui.transit

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mentalmachines.travel_app.domain.StopState
import com.mentalmachines.travel_app.domain.TransitStop
import com.mentalmachines.travel_app.ui.components.TravelTopAppBar

// ============================================================================
// ATOMS
// ============================================================================

@Composable
fun TimelineNode(
    isFirst: Boolean,
    isLast: Boolean,
    state: StopState,
    modifier: Modifier = Modifier
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val variantColor = MaterialTheme.colorScheme.outlineVariant

    val lineColor = if (state == StopState.PASSED || state == StopState.CURRENT) primaryColor else variantColor
    val dotColor = when (state) {
        StopState.PASSED -> primaryColor
        StopState.CURRENT -> MaterialTheme.colorScheme.error // Accent for current
        StopState.UPCOMING -> variantColor
    }

    Canvas(
        modifier = modifier
            .width(24.dp)
            .fillMaxHeight()
    ) {
        val circleRadius = 6.dp.toPx()
        val centerX = size.width / 2f
        val centerY = 24.dp.toPx() // Anchor dot near the top of the item

        // Draw top line (if not first)
        if (!isFirst) {
            drawLine(
                color = primaryColor, // The line coming into this node is based on previous state
                start = Offset(centerX, 0f),
                end = Offset(centerX, centerY - circleRadius),
                strokeWidth = 2.dp.toPx()
            )
        }

        // Draw bottom line (if not last)
        if (!isLast) {
            drawLine(
                color = lineColor,
                start = Offset(centerX, centerY + circleRadius),
                end = Offset(centerX, size.height),
                strokeWidth = 2.dp.toPx()
            )
        }

        // Draw dot
        if (state == StopState.CURRENT) {
            drawCircle(
                color = dotColor,
                radius = circleRadius,
                center = Offset(centerX, centerY)
            )
            drawCircle(
                color = dotColor.copy(alpha = 0.3f),
                radius = circleRadius * 2,
                center = Offset(centerX, centerY)
            )
        } else {
            drawCircle(
                color = dotColor,
                radius = circleRadius,
                center = Offset(centerX, centerY),
                style = if (state == StopState.UPCOMING) Stroke(width = 2.dp.toPx()) else androidx.compose.ui.graphics.drawscope.Fill
            )
        }
    }
}

@Composable
fun TransitTimeText(
    time: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = time,
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Bold,
        modifier = modifier
    )
}

@Composable
fun TransitLocationText(
    locationName: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = locationName,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onSurface,
        fontWeight = FontWeight.Bold,
        modifier = modifier
    )
}

@Composable
fun TransitDetailsText(
    details: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = details,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = modifier
    )
}

// ============================================================================
// MOLECULES
// ============================================================================

@Composable
fun TransitStopContent(
    stop: TransitStop,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        TransitTimeText(time = stop.time)
        TransitLocationText(locationName = stop.locationName)
        if (stop.details.isNotEmpty()) {
            TransitDetailsText(details = stop.details)
        }
    }
}

// ============================================================================
// ORGANISMS
// ============================================================================

@Composable
fun TransitStopRow(
    stop: TransitStop,
    isFirst: Boolean,
    isLast: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min) // Critical to make the Canvas fill height
            .padding(horizontal = 16.dp)
    ) {
        TimelineNode(
            isFirst = isFirst,
            isLast = isLast,
            state = stop.state
        )
        Spacer(modifier = Modifier.width(8.dp))
        TransitStopContent(stop = stop)
    }
}

@Composable
fun TransitRouteList(
    transitStops: List<TransitStop>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        itemsIndexed(transitStops) { index, stop ->
            TransitStopRow(
                stop = stop,
                isFirst = index == 0,
                isLast = index == transitStops.lastIndex
            )
        }
    }
}

// ============================================================================
// TEMPLATES
// ============================================================================

@Composable
fun TransitContentTemplate(
    transitStops: List<TransitStop>,
    isLoading: Boolean,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TravelTopAppBar(
                title = "Transit Route",
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                TransitRouteList(transitStops = transitStops)
            }
        }
    }
}

// ============================================================================
// PAGES
// ============================================================================

@Composable
fun TransitScreen(
    onBackClick: () -> Unit,
    viewModel: TransitViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState

    TransitContentTemplate(
        transitStops = uiState.transitStops,
        isLoading = uiState.isLoading,
        onBackClick = onBackClick
    )
}
