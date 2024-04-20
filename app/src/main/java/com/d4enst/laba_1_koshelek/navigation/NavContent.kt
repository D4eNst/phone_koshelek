package com.d4enst.laba_1_koshelek.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.d4enst.laba_1_koshelek.pages.PatternsCRUD
import com.d4enst.laba_1_koshelek.pages.PatternsList

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
            PatternsList(
                navController,
                Modifier.fillMaxSize()
            )
        }
        composable(Page.PATTERN_CRUD.route){
            PatternsCRUD(
                navController,
                Modifier.fillMaxSize()
            )
        }
    }
}