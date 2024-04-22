package com.d4enst.laba_1_koshelek.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.d4enst.laba_1_koshelek.addParams
import com.d4enst.laba_1_koshelek.db.models.Category
import com.d4enst.laba_1_koshelek.navigation.Page
import com.d4enst.laba_1_koshelek.navigation.PageParam
import com.d4enst.laba_1_koshelek.ui.theme.Laba_1_koshelekTheme
import com.d4enst.laba_1_koshelek.view_models.CategoryViewModel
import kotlinx.coroutines.flow.Flow

@Composable
fun PatternsList(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    getAllCategories: () -> Flow<List<Category>>,
){
    Scaffold(
        topBar = {},
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(
                        Page.PATTERN_CRUD.route
                            .addParams(PageParam.DEFAULT_CATEGORY.value)
                    )
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_add_24),
                    contentDescription = null
                )
            }
        }
    ) { innerPadding ->
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .padding(innerPadding)
        ) {
            val categoryList by getAllCategories().collectAsState(initial = emptyList())

            LazyColumn(
                modifier = Modifier.weight(.7F),
                verticalArrangement = Arrangement.Center
            ) {
                items(categoryList) { category ->
                    Card(
                        modifier = Modifier
                            .width(200.dp)
                            .height(80.dp)
                            .padding(vertical = 8.dp)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = category.categoryName, style = MaterialTheme.typography.displaySmall)
                        }
                    }
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    val navController = rememberNavController()
    val categoryViewModel: CategoryViewModel = viewModel(factory= CategoryViewModel.Factory)

    Laba_1_koshelekTheme {
        PatternsList(
            navController,
            getAllCategories = categoryViewModel::getAllCategories
        )
    }
}