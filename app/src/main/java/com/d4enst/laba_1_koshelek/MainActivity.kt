package com.d4enst.laba_1_koshelek

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.d4enst.laba_1_koshelek.navigation.NavContent
import com.d4enst.laba_1_koshelek.navigation.Page
import com.d4enst.laba_1_koshelek.ui.theme.Laba_1_koshelekTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController: NavHostController = rememberNavController()
            val position by navController.currentBackStackEntryAsState()
            Laba_1_koshelekTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MainView(position, navController)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(position: NavBackStackEntry?, navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(when(position?.destination?.route){
//                    Page.NAME_PAGE.route -> stringResource(R.string.SOME_PAGE_TITLE)
                    else -> stringResource(R.string.main_page_title)
                })},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                navigationIcon = {
                    if (position?.destination?.route != Page.MAIN.route)
                        IconButton(onClick = {navController.popBackStack()}) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                                contentDescription = null
                            )
                        }
                }
            )
        }
    ) { innerPadding ->
        NavContent(
            navController = navController,
            modifier = Modifier
                .padding(innerPadding)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainViewPreview() {
    val navController = rememberNavController()
    val position by navController.currentBackStackEntryAsState()
    Laba_1_koshelekTheme {
        MainView(position, navController)
    }
}