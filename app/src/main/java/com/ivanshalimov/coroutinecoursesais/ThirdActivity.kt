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
import androidx.compose.ui.tooling.preview.Preview
import com.ivanshalimov.coroutinecoursesais.ui.theme.CoroutineCourseSAISTheme
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

class ThirdActivity : ComponentActivity() {

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

    private fun log(text: String) {
        Log.d("TAG", "${formatter.format(Date())} $text [${Thread.currentThread().name}]")
    }


    fun onRun() {
        val handler = CoroutineExceptionHandler { context, exception ->
            log("$exception was handled in Coroutine_${context[CoroutineName]?.name}")
        }

        val scope = CoroutineScope(Job() + Dispatchers.Default + handler)

        scope.launch(CoroutineName("1")) {

            launch(CoroutineName("1_1")) {
                TimeUnit.MILLISECONDS.sleep(500)
                Integer.parseInt("a")
            }

            launch(CoroutineName("1_2")) {
                TimeUnit.MILLISECONDS.sleep(1000)
            }
        }

        scope.launch(CoroutineName("2")) {

            launch(CoroutineName("2_1")) {
                TimeUnit.MILLISECONDS.sleep(1000)
            }

            launch(CoroutineName("2_2")) {
                TimeUnit.MILLISECONDS.sleep(1000)
            }
        }
    }

    fun onRun1() {
        val handler = CoroutineExceptionHandler { context, exception ->
            log("$exception was handled in Coroutine_${context[CoroutineName]?.name}")
        }

        val scope = CoroutineScope(Job() + Dispatchers.Default + handler)

        scope.launch(CoroutineName("1")) {

            launch(CoroutineName("1_1")) {
                TimeUnit.MILLISECONDS.sleep(1000)
                log("exception")
                Integer.parseInt("a")
            }

            launch(CoroutineName("1_2")) { repeatIsActive() }
        }

        scope.launch(CoroutineName("2")) {

            launch(CoroutineName("2_1")) { repeatIsActive() }

            launch(CoroutineName("2_2"))  { repeatIsActive() }
        }
    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Column {
            Text(text = "Lesson 14")
            Row {
                Button(onClick = { onRun() }) {
                    Text("onRun")
                }
                Button(onClick = { onRun1() }) {
                    Text("onRun1")
                }
            }
        }
    }

    fun CoroutineScope.repeatIsActive() {
        repeat(5) {
            TimeUnit.MILLISECONDS.sleep(300L)
            log("Coroutine_${coroutineContext[CoroutineName]?.name} isActive $isActive")
        }
    }
}
