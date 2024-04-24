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
import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.d4enst.laba_1_koshelek.addParams
import com.d4enst.laba_1_koshelek.db.models.Category
import com.d4enst.laba_1_koshelek.navigation.Page
import com.d4enst.laba_1_koshelek.navigation.PageParam
import com.d4enst.laba_1_koshelek.ui.theme.Laba_1_koshelekTheme
import com.d4enst.laba_1_koshelek.view_models.CategoryViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow

@Composable
fun PatternsList(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    getAllCategories: () -> Flow<List<Category>>,
    deleteCategory: (category: Category) -> Job,
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
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = modifier
                .padding(innerPadding)
        ) {
            val categoryList by getAllCategories().collectAsState(initial = emptyList())
            Text(
                text = stringResource(R.string.patern_list_title),
                fontSize = 32.sp,
                modifier = Modifier
                    .padding(24.dp)
            )
            if (categoryList.isEmpty())
                Text(
                    text = stringResource(R.string.categories_not_found),
                    fontSize = 32.sp,
                    modifier = Modifier
                        .padding(24.dp)
                )
            LazyColumn(
                modifier = Modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top
            ) {
                items(categoryList) { category ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                    ) {
                        Card(
                            onClick = {

                            },
                            modifier = Modifier
                                .height(80.dp)
                                .padding(vertical = 8.dp)
                                .fillMaxSize(0.5f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentAlignment = Alignment.Center

                            ) {
                                Text(text = category.categoryName, style = MaterialTheme.typography.displaySmall)

                            }
                        }
                        Button(
                            onClick = {
                                navController.navigate(
                                    Page.PATTERN_CRUD.route
                                        .addParams(category.id.toString())
                                )
                            }
                        ) {
                            Icon(Icons.Filled.Info, contentDescription = stringResource(R.string.done_icon_description))
                        }
                        Button(
                            onClick = {
                                deleteCategory(category)
                            }
                        ) {
                            Icon(Icons.Filled.Delete, contentDescription = stringResource(R.string.done_icon_description))
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
            Modifier,
            categoryViewModel::getAllCategories,
            categoryViewModel::deleteCategory,
        )
    }
}