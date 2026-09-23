package com.mentalmachines.travel_app.ui

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mentalmachines.travel_app.ui.home.HomeScreen
import com.mentalmachines.travel_app.ui.Trips.TripDetailScreen
import com.mentalmachines.travel_app.ui.Trips.TripDraftEditScreen
import com.mentalmachines.travel_app.ui.packlist.PackListScreen
import com.mentalmachines.travel_app.ui.map.MapExploreScreen
import com.mentalmachines.travel_app.ui.transit.TransitScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.Home.route,
    ) {
        composable(route = Screens.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(
            route = Screens.Details.route + "/{tripId}",
            arguments = listOf(
                navArgument("tripId") {
                    type = NavType.StringType
                }
            ),
            enterTransition = {
                slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn()
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -1000 }) + fadeOut()
            },
            popEnterTransition = {
                slideInHorizontally(initialOffsetX = { -1000 }) + fadeIn()
            },
            popExitTransition = {
                slideOutHorizontally(targetOffsetX = { 1000 }) + fadeOut()
            }
        ) { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId").orEmpty()
            TripDetailScreen(
                tripId = tripId,
                onExploreMapClick = {
                    navController.navigate(Screens.MapExplore.route + "/$tripId")
                },
                onTransitClick = {
                    navController.navigate(Screens.Transit.route + "/$tripId")
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = Screens.PackList.route,
            enterTransition = {
                slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn()
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -1000 }) + fadeOut()
            },
            popEnterTransition = {
                slideInHorizontally(initialOffsetX = { -1000 }) + fadeIn()
            },
            popExitTransition = {
                slideOutHorizontally(targetOffsetX = { 1000 }) + fadeOut()
            }
        ) {
            PackListScreen {
                navController.popBackStack()
            }
        }
        composable(
            route = Screens.MapExplore.route + "/{tripId}",
            arguments = listOf(
                navArgument("tripId") {
                    type = NavType.StringType
                }
            ),
            enterTransition = {
                slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn()
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -1000 }) + fadeOut()
            },
            popEnterTransition = {
                slideInHorizontally(initialOffsetX = { -1000 }) + fadeIn()
            },
            popExitTransition = {
                slideOutHorizontally(targetOffsetX = { 1000 }) + fadeOut()
            }
        ) { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId").orEmpty()
            MapExploreScreen(
                tripId = tripId,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(
            route = Screens.DraftEdit.route + "/{draftId}",
            arguments = listOf(
                navArgument("draftId") {
                    type = NavType.StringType
                }
            ),
            enterTransition = {
                slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn()
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -1000 }) + fadeOut()
            },
            popEnterTransition = {
                slideInHorizontally(initialOffsetX = { -1000 }) + fadeIn()
            },
            popExitTransition = {
                slideOutHorizontally(targetOffsetX = { 1000 }) + fadeOut()
            }
        ) {
            TripDraftEditScreen(
                onBackClick = { navController.popBackStack() },
                onTripConverted = { convertedTripId ->
                    navController.popBackStack()
                    navController.navigate(Screens.Details.route + "/$convertedTripId")
                }
            )
        }
        composable(
            route = Screens.Transit.route + "/{tripId}",
            arguments = listOf(
                navArgument("tripId") {
                    type = NavType.StringType
                }
            ),
            enterTransition = {
                slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn()
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -1000 }) + fadeOut()
            },
            popEnterTransition = {
                slideInHorizontally(initialOffsetX = { -1000 }) + fadeIn()
            },
            popExitTransition = {
                slideOutHorizontally(targetOffsetX = { 1000 }) + fadeOut()
            }
        ) { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId").orEmpty()
            TransitScreen(
                tripId = tripId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}

sealed class Screens(val route : String){
    object Home : Screens("home_screen")
    object Details : Screens("details_screen")
    object PackList : Screens("pack_list_screen")
    object MapExplore : Screens("map_explore_screen")
    object DraftEdit : Screens("draft_edit_screen")
    object Transit : Screens("transit_screen")
}
