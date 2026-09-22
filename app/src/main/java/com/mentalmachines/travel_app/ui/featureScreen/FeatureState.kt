package com.mentalmachines.travel_app.ui.featureScreen

data class FeatureState(
    var isLoading: Boolean = true,
    var isFailed: Boolean = false,
    var errorMessage: String = ""
)
