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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.d4enst.laba_1_koshelek.db.dao.CategoryLabelDao
import com.d4enst.laba_1_koshelek.db.models.CategoryLabel
import com.d4enst.laba_1_koshelek.view_models.ObjectViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ObjectCRUD(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    categoryId: Long,
    objectId: Long,
    viewModel: ObjectViewModel,
){
    var isEditable by remember { mutableStateOf(objectId == 0L) }
    viewModel.categoryObject.categoryId = categoryId
    viewModel.currentObjectId = objectId

//    val categoryLabels by viewModel.getAllCategoryLabelsByCategoryId(categoryId)
//        .collectAsState(initial = emptyList())

    LaunchedEffect(viewModel.currentObjectId) {
        viewModel.collectObject()
    }

    LaunchedEffect(viewModel.currentObjectId) {
        viewModel.collectCategoryLabels()
    }

    LaunchedEffect(viewModel.currentObjectId) {
        viewModel.collectObjectsValues()
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
                    Text(viewModel.categoryObjectNameInput, fontSize = 32.sp)
                },
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val changed = viewModel.createOrChangeCategoryObject(isEditable)
                    if (changed)
                        isEditable = !isEditable
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
                itemsIndexed(viewModel.categoryObjectsStates) { i, _ ->
                    if (i > 0)
                        Text(
                            text = viewModel.categoryLabelsStates[i-1].categoryLabelName,
                            modifier = Modifier
                                .padding(start = 24.dp, end = 16.dp, top = 16.dp)
                        )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(top = 16.dp)

                    ) {
                        OutlinedTextField(
                            value = viewModel.categoryObjectsStates[i],
                            modifier = Modifier
                                .padding(0.dp)
                                .focusRequester(focusRequester),
                            onValueChange = {
                                viewModel.categoryObjectsStates[i] = it
                                if (i == 0){
                                    viewModel.categoryObjectNameInput = it
                                    viewModel.categoryObject.categoryObjectName = it
                                }
                            },
                            enabled = isEditable,
                            label = {
                                if (i == 0)
                                    Text(stringResource(R.string.object_title_name_titile))
                            },
                            keyboardOptions = KeyboardOptions(
                                imeAction = if (i + 1 < viewModel.categoryObjectsStates.size) ImeAction.Next
                                else ImeAction.Done
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
//                        if (i + 1 > 1 && viewModel.states.size > 2 && isEditable)
//                            Button(
//                                onClick = {
//                                    viewModel.removeLabelInUI(i)
//                                }
//                            ) {
//                                Icon(Icons.Filled.Delete, contentDescription = stringResource(R.string.done_icon_description))
//                            }
                    }
//                    if (i + 1 == viewModel.states.size && isEditable)
//                        Button(
//                            onClick = {
//                                viewModel.addLabelInUI()
//                                currentScope.launch {
//                                    listState.animateScrollToItem(i)
//                                    focusRequester.requestFocus() // Запросить фокус
//                                }
//                            },
//                            modifier = Modifier
//                                .padding(top = 16.dp)
//                        ) {
//                            Icon(Icons.Filled.Add, contentDescription = stringResource(R.string.done_icon_description))
//                        }
                }
            }
        }
    }

    if (viewModel.showDialog)
        AlertDialog(
            onDismissRequest = { viewModel.showDialog = false},
            title = { Text(text = stringResource(R.string.warning)) },
            text = { Text(stringResource(R.string.category_title_null_warning)) },
            confirmButton = {
                Button({ viewModel.showDialog = false }) {
                    Text(stringResource(R.string.ok_text), fontSize = 22.sp)
                }
            }
        )
}
