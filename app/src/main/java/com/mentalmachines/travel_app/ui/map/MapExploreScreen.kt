package com.mentalmachines.travel_app.ui.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mentalmachines.travel_app.domain.InterestPlace
import com.mentalmachines.travel_app.ui.components.TravelTopAppBar
import com.mentalmachines.travel_app.ui.theme.TravelAmber
import com.mentalmachines.travel_app.ui.theme.TravelMint
import com.mentalmachines.travel_app.ui.theme.TravelMintLight

// ============================================================================
// ATOMS
// ============================================================================

@Composable
fun PlaceMarkerPin(
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .background(
                color = if (isSelected) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                shape = CircleShape
            )
            .size(36.dp)
    ) {
        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = "Marker Pin",
            tint = Color.White,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
fun RatingBadge(
    rating: Double,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(TravelAmber, shape = RoundedCornerShape(6.dp))
            .padding(horizontal = 6.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = "Star",
            tint = Color.White,
            modifier = Modifier.size(12.dp)
        )
        Text(
            text = rating.toString(),
            color = Color.White,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Composable
fun PricePointText(
    price: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = price,
        color = MaterialTheme.colorScheme.secondary,
        style = MaterialTheme.typography.titleSmall,
        modifier = modifier
    )
}

@Composable
fun DescriptorTagChip(
    tag: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = tag,
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f),
                shape = RoundedCornerShape(6.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
    )
}

// ============================================================================
// MOLECULES
// ============================================================================

@Composable
fun PlaceCardHeader(
    name: String,
    foodType: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = name,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = foodType,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun PlaceMetaRow(
    rating: Double,
    pricePoint: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RatingBadge(rating = rating)
        PricePointText(price = pricePoint)
    }
}

// ============================================================================
// ORGANISMS
// ============================================================================

@Composable
fun PlaceDetailOverlayCard(
    place: InterestPlace,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            PlaceCardHeader(name = place.name, foodType = place.foodType)
            PlaceMetaRow(rating = place.rating, pricePoint = place.pricePoint)
            
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                place.tags.forEach { tag ->
                    DescriptorTagChip(tag = tag)
                }
            }
        }
    }
}

@Composable
fun InteractiveMapViewContainer(
    places: List<InterestPlace>,
    selectedPlaceId: String?,
    onPlaceSelect: (InterestPlace) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(TravelMintLight, TravelMint)
                )
            )
    ) {
        places.forEach { place ->
            Box(
                modifier = Modifier
                    .offset(x = place.offsetX.dp, y = place.offsetY.dp)
            ) {
                PlaceMarkerPin(
                    isSelected = selectedPlaceId == place.id,
                    onClick = { onPlaceSelect(place) }
                )
            }
        }
    }
}

// ============================================================================
// TEMPLATES
// ============================================================================

@Composable
fun MapExploreContentTemplate(
    places: List<InterestPlace>,
    selectedPlace: InterestPlace?,
    isLoading: Boolean,
    onPlaceSelect: (InterestPlace) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TravelTopAppBar(
                title = "Explore Map Details",
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                InteractiveMapViewContainer(
                    places = places,
                    selectedPlaceId = selectedPlace?.id,
                    onPlaceSelect = onPlaceSelect,
                    modifier = Modifier.fillMaxSize()
                )

                if (selectedPlace != null) {
                    PlaceDetailOverlayCard(
                        place = selectedPlace,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}

// ============================================================================
// PAGES
// ============================================================================

@Composable
fun MapExploreScreen(
    tripId: String,
    onBackClick: () -> Unit,
    viewModel: MapExploreViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState

    MapExploreContentTemplate(
        places = uiState.places,
        selectedPlace = uiState.selectedPlace,
        isLoading = uiState.isLoading,
        onPlaceSelect = { viewModel.selectPlace(it) },
        onBackClick = onBackClick
    )
}
