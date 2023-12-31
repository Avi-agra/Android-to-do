package com.example.myapplication

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
//    val tasks: Map<String, String> = mapOf(
//        Pair("Title1", "Body1"),
//        Pair("Title2", "Body2"),
//        Pair("Title3", "Body3"),
//        Pair("Title4", "Body4")
//    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TasksScreen(this)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen(context: ComponentActivity) {
    var bottomSheetState =
        rememberStandardBottomSheetState(initialValue = SheetValue.Hidden, skipHiddenState = false)
    var bottomSheetScafflodState =
        rememberBottomSheetScaffoldState(bottomSheetState = bottomSheetState)
    var scope = rememberCoroutineScope()
    var tasks by remember {
        mutableStateOf(listOf<TasksEntity>())
    }
    val viewModel = ViewModelProvider(context).get(TasksViewModel::class.java)
    viewModel.readAllLiveData.observe(context, Observer { task ->
        tasks=task
    })
    BottomSheetScaffold(
        sheetPeekHeight = Dp(0f),
        scaffoldState = bottomSheetScafflodState,
        sheetContent = {
            BottomSheetContent(scope, bottomSheetState, context)
        }) {
        MyApplicationTheme {
            TasksList(tasks, scope, bottomSheetState,context)
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TasksList(
    tasks: List<TasksEntity>,
    scope: CoroutineScope,
    bottomSheetState: SheetState,
    context: ComponentActivity
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.End,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        ListGenerator(context,list = tasks)
        Button(onClick = {
            scope.launch {
                if (bottomSheetState.currentValue == SheetValue.Hidden)
                    bottomSheetState.expand()
                else
                    bottomSheetState.hide()
            }
        }, Modifier.padding(end = Dp(10f), bottom = Dp(10f))) {
            Text(text = "Add New Task")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BottomSheetContent(
    scope: CoroutineScope,
    bottomSheetState: SheetState,
    context: ComponentActivity
) {
    var title by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var body by remember {
        mutableStateOf(TextFieldValue(""))
    }
    OutlinedTextField(
        modifier = Modifier
            .padding(horizontal = Dp(20f))
            .fillMaxWidth(),
        value = title,
        onValueChange = {
            title = it
        },
        label = { Text(text = "Title") },
    )
    OutlinedTextField(
        modifier = Modifier
            .padding(horizontal = Dp(20f))
            .fillMaxWidth(),
        value = body,
        onValueChange = {
            body = it
        },
        label = { Text(text = "Body") },
    )
    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
        Button(
            onClick = {
                var viewModel: TasksViewModel
                viewModel = ViewModelProvider(context).get(TasksViewModel::class.java)
                viewModel.addTask(TasksEntity(0, title.text, body.text))
                title = TextFieldValue("")
                body = TextFieldValue("")
                scope.launch { bottomSheetState.hide() }
            }, modifier = Modifier

                .padding(all = Dp(10f))
        ) {
            Text(text = "Save")
        }
        Button(
            onClick = {
                scope.launch { bottomSheetState.hide() }
            }, modifier = Modifier
                .padding(all = Dp(10f))
        ) {
            Text(text = "Close")
        }
    }
}

@Composable
fun ListItem(item: TasksEntity,context: ComponentActivity) {
    Column(verticalArrangement = Arrangement.Top) {
        var showDetails = ItemTopic(context,item)
        ItemBody(showDetails, item)
    }
}

@Composable
private fun ColumnScope.ItemBody(
    showDetails: Boolean,
    item: TasksEntity
) {
    AnimatedVisibility(visible = showDetails) {
        Column(
            modifier = Modifier
                .padding(start = Dp(20f), end = Dp(20f), bottom = Dp(14f), top = Dp(0f))
                .background(Color.Magenta)
                .height(Dp(50f))
                .fillMaxWidth()
        ) {
            Text(
                text = item.body,
                style = TextStyle(color = Color.White),
                modifier = Modifier.padding(all = Dp(12f))
            )
        }
    }
}

@Composable
private fun ItemTopic(
    context: ComponentActivity,
    item: TasksEntity
): Boolean {
    var showDetails1 by remember {
        mutableStateOf(false)
    }
    Row(
        modifier = Modifier
            .padding(start = Dp(10f), end = Dp(10f), top = Dp(10f))
            .border(width = Dp(1f), shape = RoundedCornerShape(Dp(10f)), color = Color.Magenta),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = item.title,
            modifier = Modifier
                .weight(3f)
                .padding(start = Dp(10f), top = Dp(2.5f), bottom = Dp(2.5f)),
            style = TextStyle(
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight(weight = 15),
                fontSize = 20.sp
            )
        )
        IconButton(
            onClick = { showDetails1 = !showDetails1 },
            modifier = Modifier
                .weight(1f)
                .width(Dp(20f))
                .padding(end = Dp(5f), top = Dp(2.5f), bottom = Dp(2.5f))
        ) {
            if (showDetails1) {
                Icon(
                    imageVector = Icons.Rounded.KeyboardArrowUp, contentDescription = null,
                    Modifier
                        .border(
                            shape = RoundedCornerShape(50),
                            width = Dp(2.5f),
                            color = Color.Blue
                        )
                )
            } else {
                Icon(
                    imageVector = Icons.Rounded.KeyboardArrowDown, contentDescription = null,
                    Modifier
                        .border(
                            shape = RoundedCornerShape(50),
                            width = Dp(2.5f),
                            color = Color.Blue
                        )
                )
            }
        }
        IconButton(onClick = {
            var viewModel: TasksViewModel
            viewModel = ViewModelProvider(context).get(TasksViewModel::class.java)
            viewModel.deleteTask(item.id)
        }) {
            Icon(
                imageVector = Icons.Rounded.Delete, contentDescription = null
            )
        }
    }
    return showDetails1
}

@Composable
fun ListGenerator(context: ComponentActivity,list: List<TasksEntity>) {
    LazyColumn() {
        items(list.toList()) { item ->
            ListItem(item = item,context)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
    }
}