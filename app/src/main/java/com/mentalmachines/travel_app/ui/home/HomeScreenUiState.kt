package com.mentalmachines.travel_app.ui.home

import com.mentalmachines.travel_app.domain.Details

data class HomeScreenUiState(
    val detail: Details = Details(),
    val offline: Boolean = false
) {
    // val formattedUserSince = formatDate(detail.userSince)
}