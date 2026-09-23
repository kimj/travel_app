package com.mentalmachines.travel_app.ui.packlist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mentalmachines.travel_app.domain.PackItem
import com.mentalmachines.travel_app.repository.PackListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PackListUiState(
    val days: Int = 5,
    val items: List<PackItem> = emptyList(),
    val isLoading: Boolean = true
)

@HiltViewModel
class PackListViewModel @Inject constructor(
    private val repository: PackListRepository
) : ViewModel() {

    var uiState by mutableStateOf(PackListUiState())
        private set

    init {
        viewModelScope.launch {
            repository.seedInitialItemsIfEmpty()
            repository.getPackItems().collect { itemList ->
                uiState = uiState.copy(
                    items = itemList,
                    isLoading = false
                )
            }
        }
    }

    fun updateDays(newDays: Int) {
        uiState = uiState.copy(days = newDays)
    }

    fun toggleItem(id: String) {
        viewModelScope.launch {
            val item = uiState.items.find { it.id == id } ?: return@launch
            repository.togglePacked(id, !item.isPacked)
        }
    }
}
