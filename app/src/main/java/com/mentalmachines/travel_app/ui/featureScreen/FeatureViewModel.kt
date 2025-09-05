package com.mentalmachines.travel_app.ui.featureScreen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FeatureViewModel @Inject constructor(
    // private val getDataUseCase: GetDataUseCase,
) : ViewModel(){
    /*private val _state = mutableStateOf(FeatureState())
    val state: State<FeatureState> = _state*/
    init {
/*        getDataUseCase().onEach { response ->
            when(response){
                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        isFailed = true,
                        errorMessage = response.message.toString()
                    )
                }
                is Resource.Loading -> _state.value = state.value.copy(isLoading = true)
                is Resource.Success -> {
                    response.data?.let {
                        _state.value = state.value.copy(
                            isLoading = false,
                        )
                    }
                }
            }
        }*/
    }
}
