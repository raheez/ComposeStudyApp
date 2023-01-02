package com.app.imagelisting.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.paging.ExperimentalPagingApi
import coil.annotation.ExperimentalCoilApi
import com.app.imagelisting.Screens.Home.HomeScreen
import com.app.imagelisting.Screens.Search.SearchScreen


@ExperimentalCoilApi
@ExperimentalPagingApi
@Composable
fun setUpNavGraph(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route )
    {
        composable(route = Screen.Home.route){
            HomeScreen(navController = navController)
        }
        composable(route = Screen.Search.route){
            SearchScreen(navController = navController)
        }
    }
}