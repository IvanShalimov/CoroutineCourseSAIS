package com.ivanshalimov.coroutinecoursesais

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.tooling.preview.Preview
import com.ivanshalimov.coroutinecoursesais.ui.theme.CoroutineCourseSAISTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    val scope = CoroutineScope(Job()) // the simplest creation of coroutine
    private var formatter = SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoroutineCourseSAISTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        /*scope.launch {
            Log.d("Ivan","My first coroutine")
        }
        scope.launch {
            Log.d("Ivan","first coroutine")
        }
        scope.launch {
            Log.d("Ivan","second coroutine")
        }

        Log.d("Ivan","top scope $scope")
        val job = scope.launch {
            Log.d("Ivan","first this: $this")
            this.launch {
                Log.d("Ivan","second this: $this")
            }
        }
        Log.d("Ivan","top job: $job")*/
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    private fun onRun() {
        log("onRun, start")
        scope.launch {
            log("coroutine start")
            var x = 0
            while (x < 5 && isActive) {
                delay(1000L)
                log("coroutine, ${x++}")
            }
            log("coroutine end")
        }
       /* log("middle")
        scope.launch {
            log("coroutine1 start")
            TimeUnit.MILLISECONDS.sleep(1500L)
            log("coroutine1 end")
        }*/
        log("onRun, end")
    }

    private fun onRun1() {
        scope.launch {
            log("parent coroutine start")
            val job = launch {
                log("child coroutine start")
                delay(1000L)
                log("child coroutine end")
            }
            log("parent coroutine waits to child completes")
            job.join()
            log("parent coroutine end")
        }
    }

    private fun onRun2() {

    }

    private fun onCancel() {
        log("onCancel")
        scope.cancel()
    }

    private fun log(text: String) {
        Log.d("TAG", "${formatter.format(Date())} $text [${Thread.currentThread().name}]")
    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        /*Text(
            text = "Hello $name!",
            modifier = modifier
        )*/
        Column {
            Row {
                Button(onClick = { onRun() }) {
                    Text("Run")
                }
                Button(onClick = { onCancel() }) {
                    Text("Cancel")
                }
            }
            Text(text = "Lesson 9")
            Row {
                Button(onClick = { onRun1() }) {
                    Text("Run1")
                }
                Button(onClick = { onRun2() }) {
                    Text("Run2")
                }
            }
        }
    }

}




/*
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CoroutineCourseSAISTheme {
        Greeting("Android")
    }
}*/
