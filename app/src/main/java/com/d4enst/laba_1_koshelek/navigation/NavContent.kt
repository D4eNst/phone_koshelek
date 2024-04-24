package com.d4enst.laba_1_koshelek.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.d4enst.laba_1_koshelek.pages.PatternCRUD
import com.d4enst.laba_1_koshelek.pages.PatternsList
import com.d4enst.laba_1_koshelek.view_models.CategoryViewModel

@Composable
fun NavContent(
    navController: NavHostController,
    modifier: Modifier = Modifier,
){
    NavHost(
        navController = navController,
        startDestination = Page.PATTERNS_LIST.route,
        modifier = modifier,
    ){

        composable(Page.PATTERNS_LIST.route){
            val categoryViewModel: CategoryViewModel = viewModel(factory= CategoryViewModel.Factory)
            PatternsList(
                navController,
                Modifier.fillMaxSize(),
                categoryViewModel::getAllCategories,
                categoryViewModel::deleteCategory
            )
        }
        composable(
            Page.PATTERN_CRUD.route + "/{categoryId}",
            arguments = listOf(
                navArgument("categoryId") { type = NavType.LongType },
            )
        ){
            val categoryViewModel: CategoryViewModel = viewModel(factory= CategoryViewModel.Factory)
            val categoryId = it.arguments?.getLong("categoryId") ?: 0L
            PatternCRUD(
                navController,
                Modifier.fillMaxSize(),
                categoryId,
                categoryViewModel,
            )
        }
    }
}