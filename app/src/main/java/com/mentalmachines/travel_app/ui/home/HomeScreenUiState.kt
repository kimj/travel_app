package com.mentalmachines.travel_app.ui.home

import com.mentalmachines.travel_app.domain.Details

/**
 * Represents the different states of a UI operation that involves fetching data.
 *
 * This sealed class is used to model the possible states of an asynchronous operation
 * (like fetching image details) that updates the UI. It allows for exhaustive handling
 * of these states in `when` expressions, ensuring all cases (Loading, Success, Error)
 * are considered by the UI.
 *
 * @param T The type of data associated with the success state.
 */
sealed class UiState<out T> {
    /** Indicates that the data is currently being loaded. */
    object Loading : UiState<Nothing>()

    /** Indicates that the data was successfully loaded. */
    data class Success<T>(val data: T?) :
        UiState<T>() // data can be null if success means "found nothing" but not an error

    /** Indicates that an error occurred during the data loading operation. */
    data class Error(val message: String) : UiState<Nothing>()
}
data class HomeScreenUiState(
    val detail: Details = Details(),
    val offline: Boolean = false
) {
    // val formattedUserSince = formatDate(detail.userSince)
}