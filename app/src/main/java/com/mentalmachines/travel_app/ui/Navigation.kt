package com.mentalmachines.travel_app.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mentalmachines.travel_app.ui.main.MainScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = Screens.Main.route,
    ){
        composable(route = Screens.Main.route) {
            MainScreen(navController = navController)
        }
        composable(
            route = Screens.Detail.route + "?text={text}",
            arguments = listOf(
                navArgument("text") {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) {
            DetailScreen(text = it.arguments?.getString("text"))
        }

    }
}

sealed class Screens(val route : String){
    object Main : Screens("main_screen")
    object Detail : Screens("detail_screen")
}
