package com.d4enst.laba_1_koshelek.pages

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.d4enst.laba_1_koshelek.R
import com.d4enst.laba_1_koshelek.ui.theme.Laba_1_koshelekTheme
import com.d4enst.laba_1_koshelek.view_models.CategoryViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatternCRUD(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    categoryId: Long,
    addCategory: (String) -> Job,

){
    var topBarText by remember { mutableStateOf("") }
    var categoryNameInput by remember { mutableStateOf("") }
    var isEditable by remember { mutableStateOf(categoryId == 0L) }

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
                    // todo add
                }
            ) {
                Icon(Icons.Filled.Done, contentDescription = stringResource(R.string.done_icon_desription))
            }
        },
    ) { innerPadding ->
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .padding(innerPadding)
        ) {
            // Для скрытия клавиатуры
            val kc = LocalSoftwareKeyboardController.current
            // Для работы с фокусом
            val focusManager = LocalFocusManager.current
            val focusRequester = remember { FocusRequester() }
            // Coroutine Scope внутри активности
            val currentScope = rememberCoroutineScope()
            // Для работы с полями ввода
            val listState = rememberLazyListState()
            var states = remember {
                mutableStateListOf("", "")
            }

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
                                if (i == 0)
                                    categoryNameInput = it
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
                                Icon(Icons.Filled.Delete, contentDescription = stringResource(R.string.done_icon_desription))
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
                            Icon(Icons.Filled.Add, contentDescription = stringResource(R.string.done_icon_desription))
                        }
                }
//                OutlinedTextField(
//                    value = categoryNameInput,
//                    onValueChange = { categoryNameInput = it },
//                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
//                    keyboardActions = KeyboardActions(
//                        onNext = {
//                            focusManager.moveFocus(FocusDirection.Down)
//                            currentScope.launch {
//                                listState.animateScrollToItem(1)
//                            }
//                        }
//                    ),
//                )
//                Button(onClick = {
//                    keyboardController?.hide()
//                    addCategory(categoryNameInput)
//                    categoryNameInput = ""
//                    navController.popBackStack()
//                }) {
//                    Text(stringResource(R.string.btn_detail))
//                }
            }


        }

    }
}

@Preview(showBackground = true)
@Composable
fun PatternsCRUDPreview() {
    val navController = rememberNavController()
    val categoryViewModel: CategoryViewModel = viewModel(factory= CategoryViewModel.Factory)
    Laba_1_koshelekTheme {
        PatternCRUD(
            navController,
            addCategory = categoryViewModel::addCategory,
            categoryId = 0L,
        )
    }
}