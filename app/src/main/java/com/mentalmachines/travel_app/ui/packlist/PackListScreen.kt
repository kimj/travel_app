package com.mentalmachines.travel_app.ui.packlist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mentalmachines.travel_app.domain.PackItem
import com.mentalmachines.travel_app.ui.components.TravelTopAppBar

// ============================================================================
// ATOMS
// ============================================================================

@Composable
fun PackItemCheckbox(
    isPacked: Boolean,
    onPackedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Checkbox(
        checked = isPacked,
        onCheckedChange = onPackedChange,
        modifier = modifier
    )
}

@Composable
fun ItemNameText(
    text: String,
    isPacked: Boolean,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.Medium,
        color = if (isPacked) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f) else MaterialTheme.colorScheme.onSurface,
        modifier = modifier
    )
}

@Composable
fun ItemQuantityText(
    quantity: Int,
    modifier: Modifier = Modifier
) {
    if (quantity > 0) {
        Text(
            text = "Qty: $quantity",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary,
            fontWeight = FontWeight.SemiBold,
            modifier = modifier
        )
    }
}

@Composable
fun CategoryTitleText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier
    )
}

// ============================================================================
// MOLECULES
// ============================================================================

@Composable
fun PackListItemRow(
    item: PackItem,
    days: Int,
    onTogglePacked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val computedQty = if (item.type == "PerDay") {
        maxOf(1, item.baseQuantityPerDay * days)
    } else if (item.type == "Checklist") {
        0
    } else {
        item.baseQuantityPerDay
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onTogglePacked() }
            .padding(vertical = 4.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        PackItemCheckbox(isPacked = item.isPacked, onPackedChange = { onTogglePacked() })
        Spacer(modifier = Modifier.width(8.dp))
        ItemNameText(text = item.name, isPacked = item.isPacked, modifier = Modifier.weight(1f))
        ItemQuantityText(quantity = computedQty)
    }
}

// ============================================================================
// ORGANISMS
// ============================================================================

@Composable
fun PackCategorySection(
    categoryName: String,
    items: List<PackItem>,
    days: Int,
    onToggleItem: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            CategoryTitleText(text = categoryName)
            Spacer(modifier = Modifier.height(8.dp))
            items.forEach { item ->
                PackListItemRow(
                    item = item,
                    days = days,
                    onTogglePacked = { onToggleItem(item.id) }
                )
            }
        }
    }
}

// ============================================================================
// TEMPLATES
// ============================================================================

@Composable
fun PackListContentTemplate(
    days: Int,
    onDaysChange: (Int) -> Unit,
    items: List<PackItem>,
    isLoading: Boolean,
    onToggleItem: (String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TravelTopAppBar(
                title = "Trip Packing List",
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "How many days is your trip?",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Button(onClick = { if (days > 1) onDaysChange(days - 1) }) {
                            Text("-", style = MaterialTheme.typography.titleMedium)
                        }
                        Text(
                            text = "$days Days",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Button(onClick = { onDaysChange(days + 1) }) {
                            Text("+", style = MaterialTheme.typography.titleMedium)
                        }
                    }
                }
            }

            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                val categories = items.map { it.category }.distinct()
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(categories) { category ->
                        val filteredItems = items.filter { it.category == category }
                        PackCategorySection(
                            categoryName = category,
                            items = filteredItems,
                            days = days,
                            onToggleItem = onToggleItem
                        )
                    }
                }
            }
        }
    }
}

// ============================================================================
// PAGES
// ============================================================================

@Composable
fun PackListScreen(
    onBackClick: () -> Unit
) {
    val viewModel: PackListViewModel = hiltViewModel()
    val uiState = viewModel.uiState

    PackListContentTemplate(
        days = uiState.days,
        onDaysChange = { viewModel.updateDays(it) },
        items = uiState.items,
        isLoading = uiState.isLoading,
        onToggleItem = { viewModel.toggleItem(it) },
        onBackClick = onBackClick
    )
}
