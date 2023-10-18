package com.example.threadingdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.threadingdemo.ui.theme.ThreadingDemoTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThreadingDemoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ThreadingDemo()
                }
            }
        }
    }
}

@Composable
fun ThreadingDemo() {
    var thread1Text by remember { mutableStateOf("Thread 1: ") }
    var thread2Text by remember { mutableStateOf("Thread 2: ") }

    LaunchedEffect(true) {
        val job1 = launch {
            for (i in 1..5) {
                withContext(Dispatchers.Main) {
                    thread1Text += "Count $i "
                }
                Thread.sleep(1000)
            }
        }

        val job2 = launch {
            for (i in 1..5) {
                withContext(Dispatchers.Main) {
                    thread2Text += "Count $i "
                }
                Thread.sleep(1000)
            }
        }

        job1.join()
        job2.join()
    }

    Column {
        Greeting("Android")
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = thread1Text)
        Text(text = thread2Text)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}
