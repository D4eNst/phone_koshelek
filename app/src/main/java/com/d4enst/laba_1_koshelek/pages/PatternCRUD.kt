package com.d4enst.laba_1_koshelek.pages

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.d4enst.laba_1_koshelek.R
import com.d4enst.laba_1_koshelek.ui.theme.Laba_1_koshelekTheme
import com.d4enst.laba_1_koshelek.view_models.CategoryViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun PatternCRUD(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    addCategory: (String) -> Job,
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
            var categoryNameInput by remember { mutableStateOf("") }
            val kc = LocalSoftwareKeyboardController.current
            val focusManager = LocalFocusManager.current
            val currentScope = rememberCoroutineScope()
            val listState = rememberLazyListState()
            val states = remember {
                mutableStateListOf("1", "2", "3",
                    "4", "5", "6", "7", "8", "9", "10", "11", "12", "13")
            }

            LazyColumn (
//                state = listState,
                modifier = Modifier.fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures(onTap = {
                            kc?.hide()
                        })
                    }
            ) {
                itemsIndexed(states) { i, _ ->
                    OutlinedTextField(value = states[i],
                        modifier = Modifier.padding(top = 16.dp),
                        onValueChange = {
                            states[i] = it
                        },
                        label = {
                            Text("Text field ${i + 1}")
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
                            }
                        ),
                    )

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
        )
    }
}