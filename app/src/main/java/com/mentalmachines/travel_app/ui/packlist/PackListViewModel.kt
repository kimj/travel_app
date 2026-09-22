package com.mentalmachines.travel_app.ui.packlist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class PackListUiState(
    val days: Int = 5,
    val packedItemIds: Set<String> = emptySet()
)

@HiltViewModel
class PackListViewModel @Inject constructor() : ViewModel() {

    var uiState by mutableStateOf(PackListUiState())
        private set

    fun updateDays(newDays: Int) {
        uiState = uiState.copy(days = newDays)
    }

    fun toggleItem(id: String) {
        val currentPacked = uiState.packedItemIds.toMutableSet()
        if (currentPacked.contains(id)) {
            currentPacked.remove(id)
        } else {
            currentPacked.add(id)
        }
        uiState = uiState.copy(packedItemIds = currentPacked)
    }
}
