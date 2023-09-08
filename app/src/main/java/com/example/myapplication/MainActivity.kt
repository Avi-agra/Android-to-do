package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
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
    var showDetails by remember {
        mutableStateOf(false)
    }
    Column {
        Row(modifier=Modifier.padding(all = Dp(10f)) ) {
            Text(text = item.first, modifier = Modifier.weight(3f))
            Button(onClick = { showDetails = !showDetails }, modifier = Modifier.weight(1f)) {
                if (!showDetails)
                    Text(text = "Show details")
                else
                    Text(text = "Hide Details")
            }
        }
        if (showDetails)
            Text(text = item.second)
    }
}

@Composable
fun ListGenerator(list: Map<String, String>) {
    LazyColumn() {
        items(list.toList()) { item ->
            ListItem(item = item)
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    MyApplicationTheme {
//        ListGenerator(list = mapOf(Pair("a","b")))
//    }
//}