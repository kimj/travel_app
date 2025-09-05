package com.mentalmachines.travel_app.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.mentalmachines.travel_app.ui.details.DetailsViewModel
import javax.inject.Inject

@Composable
fun HomeScreen() {
    val viewModel = hiltViewModel<HomeScreenViewModel>()

    TravelCardListView()
}


@Composable
fun TravelCardListView() {
    Column {

    }
}
