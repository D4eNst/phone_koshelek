package com.d4enst.laba_1_koshelek.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.d4enst.laba_1_koshelek.R
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.d4enst.laba_1_koshelek.ui.theme.Laba_1_koshelekTheme

@Composable
fun PatternsList(
    navController: NavHostController,
    modifier: Modifier = Modifier,
){
    Scaffold(
        topBar = {},
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // todo add
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_add_24),
                    contentDescription = null
                )
            }
        }
    ) { innerPadding ->
//      temp example
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .padding(innerPadding)
        ) {
            Text(stringResource(R.string.main_text))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
//                EXAMPLE:
//                Button(onClick = {
//                    navController.navigate(Page.DETAILS.route)
//                }) {
//                    Text(stringResource(R.string.btn_detail))
//                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    val navController = rememberNavController()
    Laba_1_koshelekTheme {
        PatternsList(navController)
    }
}