package com.mentalmachines.travel_app.domain

data class TransitStop(
    val id: String,
    val time: String,
    val locationName: String,
    val details: String,
    val state: StopState
)

enum class StopState {
    PASSED, CURRENT, UPCOMING
}
