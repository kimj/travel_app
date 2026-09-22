package com.mentalmachines.travel_app.ui.packlist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

// ============================================================================
// DATA MODELS
// ============================================================================
data class PackItemTemplate(
    val id: String,
    val name: String,
    val baseQuantityPerDay: Int,
    val category: String
)

// ============================================================================
// ATOMS: Basic building blocks
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
        fontSize = 16.sp,
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
    Text(
        text = "Qty: $quantity",
        fontSize = 14.sp,
        color = MaterialTheme.colorScheme.secondary,
        fontWeight = FontWeight.SemiBold,
        modifier = modifier
    )
}

@Composable
fun CategoryTitleText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier
    )
}

// ============================================================================
// MOLECULES: Group of atoms bonded together
// ============================================================================

@Composable
fun PackListItemRow(
    itemName: String,
    quantity: Int,
    isPacked: Boolean,
    onTogglePacked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onTogglePacked() }
            .padding(vertical = 4.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        PackItemCheckbox(isPacked = isPacked, onPackedChange = { onTogglePacked() })
        Spacer(modifier = Modifier.width(8.dp))
        ItemNameText(text = itemName, isPacked = isPacked, modifier = Modifier.weight(1f))
        ItemQuantityText(quantity = quantity)
    }
}

// ============================================================================
// ORGANISMS: Complex UI components composed of molecules and atoms
// ============================================================================

@Composable
fun PackCategorySection(
    categoryName: String,
    items: List<PackItemTemplate>,
    days: Int,
    packedItemIds: Set<String>,
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
                val computedQty = maxOf(1, item.baseQuantityPerDay * days)
                PackListItemRow(
                    itemName = item.name,
                    quantity = computedQty,
                    isPacked = packedItemIds.contains(item.id),
                    onTogglePacked = { onToggleItem(item.id) }
                )
            }
        }
    }
}

// ============================================================================
// TEMPLATES: Page-level layout focusing on content structure
// ============================================================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PackListContentTemplate(
    days: Int,
    onDaysChange: (Int) -> Unit,
    itemsTemplates: List<PackItemTemplate>,
    packedItemIds: Set<String>,
    onToggleItem: (String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Trip Packing List", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
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
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "How many days is your trip?",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Button(onClick = { if (days > 1) onDaysChange(days - 1) }) {
                            Text("-", fontSize = 18.sp)
                        }
                        Text(
                            text = "$days Days",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Button(onClick = { onDaysChange(days + 1) }) {
                            Text("+", fontSize = 18.sp)
                        }
                    }
                }
            }

            val categories = itemsTemplates.map { it.category }.distinct()
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(categories) { category ->
                    val filteredItems = itemsTemplates.filter { it.category == category }
                    PackCategorySection(
                        categoryName = category,
                        items = filteredItems,
                        days = days,
                        packedItemIds = packedItemIds,
                        onToggleItem = onToggleItem
                    )
                }
            }
        }
    }
}

// ============================================================================
// PAGES: Specific instance injected with data/state
// ============================================================================

@Composable
fun PackListScreen(
    onBackClick: () -> Unit
) {
    val viewModel: PackListViewModel = hiltViewModel()
    val uiState = viewModel.uiState

    val sampleTemplates = listOf(
        PackItemTemplate("1", "T-Shirts", 1, "Clothing"),
        PackItemTemplate("2", "Socks & Underwear", 1, "Clothing"),
        PackItemTemplate("3", "Pants / Jeans", 2, "Clothing"),
        PackItemTemplate("4", "Toothbrush & Paste", 1, "Toiletries"),
        PackItemTemplate("5", "Shampoo & Bodywash", 1, "Toiletries"),
        PackItemTemplate("6", "Phone Charger", 1, "Electronics"),
        PackItemTemplate("7", "Universal Power Adapter", 1, "Electronics")
    )

    PackListContentTemplate(
        days = uiState.days,
        onDaysChange = { viewModel.updateDays(it) },
        itemsTemplates = sampleTemplates,
        packedItemIds = uiState.packedItemIds,
        onToggleItem = { viewModel.toggleItem(it) },
        onBackClick = onBackClick
    )
}
