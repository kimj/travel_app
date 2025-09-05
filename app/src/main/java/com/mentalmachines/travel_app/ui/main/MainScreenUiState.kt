package com.mentalmachines.travel_app.ui.main

import com.mentalmachines.travel_app.domain.Details

data class MainScreenUiState(
    val detail: Details = Details(),
    val offline: Boolean = false
) {
    // val formattedUserSince = formatDate(detail.userSince)
}