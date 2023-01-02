package com.app.imagelisting.Screens.Home

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.app.imagelisting.Screens.Common.ListContent
import com.app.imagelisting.navigation.Screen

@ExperimentalCoilApi
@ExperimentalPagingApi
@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel : HomeViewModel = hiltViewModel()){

    val getAllImages = homeViewModel.getAllImages.collectAsLazyPagingItems()
    Scaffold(
        topBar = {
            HomeTopBar (
                onSearchClicked = {
                    navController.navigate(Screen.Search.route)
                }
            )
        },
        content = {
            ListContent(items = getAllImages)
        }, backgroundColor = Color.Black)
}