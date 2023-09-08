package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    val tasks: Map<String, String> = mapOf(
        Pair("Title1", "Body1"),
        Pair("Title2", "Body2"),
        Pair("Title3", "Body3"),
        Pair("Title4", "Body4")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                ListGenerator(list = tasks)
            }

        }
    }
}

@Composable
fun ListItem(item: Pair<String, String>) {
    Column(verticalArrangement = Arrangement.Top) {
        var showDetails = ItemTopic(item)
        ItemBody(showDetails, item)
    }
}

@Composable
private fun ColumnScope.ItemBody(
    showDetails: Boolean,
    item: Pair<String, String>
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
                text = item.second,
                style = TextStyle(color = Color.White),
                modifier = Modifier.padding(all = Dp(12f))
            )
        }
    }
}

@Composable
private fun ItemTopic(
    item: Pair<String, String>
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
            text = item.first,
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
    }
    return showDetails1
}

@Composable
fun ListGenerator(list: Map<String, String>) {
    LazyColumn() {
        items(list.toList()) { item ->
            ListItem(item = item)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        ListGenerator(list = mapOf(Pair("a", "b")))
    }
}