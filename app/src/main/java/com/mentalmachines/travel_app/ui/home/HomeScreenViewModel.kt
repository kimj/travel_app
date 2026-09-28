package com.mentalmachines.travel_app.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mentalmachines.travel_app.repository.DetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val detailsRepository: DetailsRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val username: String? = savedStateHandle["USERNAME"]
    var uiState by mutableStateOf(HomeScreenUiState())
        private set


    sealed interface UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent
        data class NavigateTo(val route: String) : UiEvent
    }

    private val _events = Channel<UiEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    fun onSaveClicked(id: String) = viewModelScope.launch {
        detailsRepository.save(id)                         // business logic
        _events.send(UiEvent.ShowSnackbar("Saved"))
        _events.send(UiEvent.NavigateTo("details/$id"))
    }

    init {
        username?.let {
            viewModelScope.launch(Dispatchers.IO) {
                detailsRepository.refreshDetails(it)
                detailsRepository.getUserDetails(it).collect { detail ->
                    withContext(Dispatchers.Main) {
                        uiState = if (detail == null) {
                            uiState.copy(offline = true)
                        } else {
                            uiState.copy(
                                detail = detail,
                                offline = false
                            )
                        }
                    }
                }
            }
        }
    }

}