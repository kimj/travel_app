package com.mentalmachines.travel_app.ui

import androidx.compose.runtime.Composable
import com.mentalmachines.travel_app.ui.details.DetailsScreen


@Composable
fun ComposeApp() {
    DetailsScreen()
}

object Route {
    const val USER = "user"
    const val DETAIL = "detail"
}

object Argument {
    const val USERNAME = "username"
}