package com.mentalmachines.travel_app.ui.details

import com.mentalmachines.travel_app.domain.Details

data class DetailsUiState(
    val detail: Details = Details(),
    val offline: Boolean = false
) {
    // val formattedUserSince = formatDate(detail.userSince)
}