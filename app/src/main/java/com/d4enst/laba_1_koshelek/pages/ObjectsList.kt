package com.d4enst.laba_1_koshelek.pages

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.d4enst.laba_1_koshelek.ui.theme.Laba_1_koshelekTheme

@Composable
fun ObjectsList(
    navController: NavHostController,
    modifier: Modifier = Modifier,
){

}

@Preview(showBackground = true)
@Composable
fun ObjectsListPreview() {
    val navController = rememberNavController()
    Laba_1_koshelekTheme {
        ObjectsList(navController)
    }
}