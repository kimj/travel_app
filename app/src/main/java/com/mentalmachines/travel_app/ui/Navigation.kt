package com.mentalmachines.travel_app.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mentalmachines.travel_app.ui.details.DetailsScreen
import com.mentalmachines.travel_app.ui.main.MainScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = Screens.Main.route,
    ){
        composable(route = Screens.Main.route) {
            MainScreen()
        }
        composable(
            route = Screens.Details.route + "?text={text}",
            arguments = listOf(
                navArgument("text") {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) {
            //text = it.arguments?.getString("text")
            DetailsScreen()
        }

    }
}

sealed class Screens(val route : String){
    object Main : Screens("main_screen")
    object Details : Screens("details_screen")
}
