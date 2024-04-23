package com.d4enst.laba_1_koshelek.pages

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.d4enst.laba_1_koshelek.R
import com.d4enst.laba_1_koshelek.db.models.Category
import com.d4enst.laba_1_koshelek.db.models.CategoryLabel
import com.d4enst.laba_1_koshelek.view_models.CategoryViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatternCRUD(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    categoryId: Long,
    categoryViewModel: CategoryViewModel,
){
    var showDialog by remember { mutableStateOf(false) }

    var isEditable by remember { mutableStateOf(categoryId == 0L) }

    var currentCategoryId by remember { mutableLongStateOf(categoryId) }
    var category by remember { mutableStateOf(Category()) }
    var categoryLabels by remember { mutableStateOf<List<CategoryLabel>>(emptyList()) }
    var categoryNameInput by remember { mutableStateOf(category.categoryName) }

    val states = remember {
        mutableStateListOf("", "")
    }

    LaunchedEffect(currentCategoryId) {
        categoryViewModel.getCategoryById(currentCategoryId).collect {
            if (it != null)
            {
                category = it
                categoryNameInput = category.categoryName
                states[0] = categoryNameInput
            }
        }
        categoryViewModel.getAllCategoryLabelsByCategoryId(currentCategoryId).collect { labels ->
            categoryLabels = labels
            states.subList(1, states.size).clear()
            states.addAll(1, categoryLabels.map { it.categoryLabelName })
        }
    }

    // Для скрытия клавиатуры
    val kc = LocalSoftwareKeyboardController.current
    // Для работы с фокусом
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    // Coroutine Scope внутри активности
    val currentScope = rememberCoroutineScope()
    // Для работы с полями ввода
    val listState = rememberLazyListState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(categoryNameInput, fontSize = 32.sp)
                },
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (isEditable) {
                        if (categoryNameInput != "") {
                            currentScope.launch {
                                if (category.id == 0L) {
                                    // Добавление категории, если её не существует
                                    currentCategoryId = categoryViewModel.addCategory(category).await()
                                }
                                // Удаление всех CategoryLabel и создание заново
                                categoryViewModel.deleteAllCategoryLabelByCategoryId(currentCategoryId)
                                categoryViewModel.addMultipleCategoryLabel(
                                    states.subList(1, states.size).map {
                                        CategoryLabel(0L, it, currentCategoryId)
                                    }
                                ).await()
                            }
                            isEditable = false
                        } else
                            showDialog = true
                    }
                    else {
                        isEditable = true
                    }
                }
            ) {
                Icon(
                    imageVector = if (isEditable) Icons.Filled.Done else Icons.Default.Edit,
                    contentDescription =
                    if (isEditable) stringResource(R.string.done_icon_description)
                    else stringResource(R.string.edit_icon_description)
                )
            }
        },
    ) { innerPadding ->
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .padding(innerPadding)
        ) {
            LazyColumn (
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures(onTap = {
                            kc?.hide()
                            focusManager.clearFocus()
                        })
                    },
                state = listState,
                contentPadding = PaddingValues(16.dp)
            ) {
                itemsIndexed(states) { i, _ ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(top = 16.dp)

                    ) {
                        OutlinedTextField(
                            value = states[i],
                            modifier = Modifier
                                .padding(0.dp)
                                .focusRequester(focusRequester),
                            onValueChange = {
                                states[i] = it
                                if (i == 0){
                                    categoryNameInput = it
                                    category.categoryName = categoryNameInput
                                }
                            },
                            enabled = isEditable,
                            label = {
                                Text(if (i == 0) "Название шаблона" else "${stringResource(R.string.label_name_titile)} $i")
                            },
                            keyboardOptions = KeyboardOptions(
                                imeAction = if (i + 1 < states.size) ImeAction.Next else ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = {
                                    focusManager.moveFocus(FocusDirection.Down)
                                    currentScope.launch {
                                        listState.animateScrollToItem(i)
                                    }
                                },
                                onDone = {
                                    kc?.hide()
                                    focusManager.clearFocus()
                                }
                            ),
                        )
                        if (i + 1 > 1 && states.size > 2)
                            Button(
                                onClick = {
                                    states.removeAt(index = i)
                                    // Пользователь может использовать двойное нажатие и удалить сразу несколько строк
                                    // Перестраховываюсь и добавляю поле, если он удалил все
                                    if (states.size <= 1)
                                        states.add("")
                                }
                            ) {
                                Icon(Icons.Filled.Delete, contentDescription = stringResource(R.string.done_icon_description))
                            }
                    }
                    if (i + 1 == states.size)
                        Button(
                            onClick = {
                                states.add("")
                                currentScope.launch {
                                    listState.animateScrollToItem(i)
                                    focusRequester.requestFocus() // Запросить фокус
                                }
                            },
                            modifier = Modifier
                                .padding(top = 16.dp)
                        ) {
                            Icon(Icons.Filled.Add, contentDescription = stringResource(R.string.done_icon_description))
                        }
                }
            }
        }
    }

    if (showDialog)
        AlertDialog(
            onDismissRequest = { showDialog = false},
            title = { Text(text = "Warning") },
            text = { Text("Category name cant be empty") },
            confirmButton = {
                Button({ showDialog = false }) {
                    Text("OK", fontSize = 22.sp)
                }
            }
        )
}

//@Preview(showBackground = true)
//@Composable
//fun PatternsCRUDPreview() {
//    val navController = rememberNavController()
//    val categoryViewModel: CategoryViewModel = viewModel(factory= CategoryViewModel.Factory)
//    Laba_1_koshelekTheme {
//        PatternCRUD(
//            navController,
//            addCategory = categoryViewModel::addCategory,
//            categoryId = 0L,
//        )
//    }
//}