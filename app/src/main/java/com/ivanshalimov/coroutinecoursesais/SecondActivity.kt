package com.ivanshalimov.coroutinecoursesais

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class SecondActivity : ComponentActivity() {

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

    private fun onRun() {
        val scope = CoroutineScope(EmptyCoroutineContext)
        log("scope, ${contextToString(scope.coroutineContext)}")

        scope.launch {
            log("coroutine, ${contextToString(this.coroutineContext)}")
        }
    }

    private fun onRun1() {
        val scope = CoroutineScope(Dispatchers.Main)
        log("scope, ${contextToString(scope.coroutineContext)}")

        scope.launch {
            log("coroutine, ${contextToString(this.coroutineContext)}")
        }
    }

    private fun onRun2() {
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        log("scope, ${contextToString(scope.coroutineContext)}")

        scope.launch {
            log("coroutine, level1, ${contextToString(this.coroutineContext)}")

            launch {
                log("coroutine, level2, ${contextToString(coroutineContext)}")

                launch {
                    log("coroutine, level3, ${contextToString(coroutineContext)}")
                }
            }
        }
    }

    private fun onRun3() {
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        log("scope, ${contextToString(scope.coroutineContext)}")

        scope.launch(UserData(12, "Artem", 37)) {
            log("coroutine, level1, ${contextToString(this.coroutineContext)}")

            launch(Dispatchers.Default) {
                log("coroutine, level2, ${contextToString(coroutineContext)}")

                launch {
                    log("coroutine, level3, ${contextToString(coroutineContext)}")
                }
            }
        }
    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(text = "Lesson 10")
        Row {
            Button(onClick = { onRun() }) {
                Text("onRun")
            }
            Button(onClick = { onRun1() }) {
                Text("onRun1")
            }
            Button(onClick = { onRun2() }) {
                Text("onRun2")
            }
            Button(onClick = { onRun3() }) {
                Text("onRun3")
            }
        }
    }

    private fun log(text: String) {
        Log.d("TAG", "${formatter.format(Date())} $text [${Thread.currentThread().name}]")
    }

    private fun contextToString(context: CoroutineContext): String =
        "Job = ${context[Job]}, Dispatcher = ${context[ContinuationInterceptor]}"
}


