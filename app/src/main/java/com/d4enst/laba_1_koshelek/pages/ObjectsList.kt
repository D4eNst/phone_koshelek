package com.d4enst.laba_1_koshelek.pages

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.d4enst.laba_1_koshelek.R
import com.d4enst.laba_1_koshelek.addParams
import com.d4enst.laba_1_koshelek.db.models.Category
import com.d4enst.laba_1_koshelek.db.models.CategoryObject
import com.d4enst.laba_1_koshelek.navigation.Page
import com.d4enst.laba_1_koshelek.navigation.PageParam
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlin.reflect.KFunction1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ObjectsList(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    categoryId: Long,
    getObjectsByCategoryId: KFunction1<Long, Flow<List<CategoryObject>>>,
    deleteObject: (category: CategoryObject) -> Job,
    getCategoryById: (categoryId: Long) -> Flow<Category?>
){
    val categoryState = getCategoryById(categoryId).collectAsState(initial = Category())
    val category: Category? = categoryState.value
    Scaffold(
        topBar = {
            TopAppBar(
                title = { category?.categoryName?.let { Text(it) } },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(
                        Page.OBJECT_CRUD.route
                            .addParams(
                                category?.id?.toString() ?: "0",
                                PageParam.DEFAULT_ID.value
                            )
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
            val objectsList by getObjectsByCategoryId(categoryId).collectAsState(initial = emptyList())
            if (objectsList.isEmpty())
                Text(
                    text = stringResource(R.string.objects_not_found),
                    fontSize = 32.sp,
                    modifier = Modifier
                        .padding(24.dp)
                )
            LazyColumn(
                modifier = Modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top
            ) {
                items(objectsList) {categoryObject ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                    ) {
                        Card(
                            onClick = {
                                navController.navigate(
                                    Page.OBJECT_CRUD.route
                                        .addParams(
                                            category?.id?.toString() ?: "0",
                                            categoryObject.id.toString()
                                        )
                                )
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
                                Text(
                                    text = categoryObject.categoryObjectName,
                                    style = MaterialTheme.typography.displaySmall
                                )

                            }
                        }
//                        Button(
//                        onClick = {
//
//                        }
//
//                        ) {
//                            Icon(
//                                Icons.Filled.Info,
//                                contentDescription = stringResource(R.string.done_icon_description)
//                            )
//                        }
                        Button(
                            onClick = {
                                deleteObject(categoryObject)
                            }
                        ) {
                            Icon(
                                Icons.Filled.Delete,
                                contentDescription = stringResource(R.string.done_icon_description)
                            )
                        }
                    }

                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun ObjectsListPreview() {
//    val navController = rememberNavController()
//    Laba_1_koshelekTheme {
//        ObjectsList(navController)
//    }
//}