package com.ivanshalimov.coroutinecoursesais

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.updateTransition
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.resume

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

    private fun onDR1() {
        val scope = CoroutineScope(Dispatchers.Default)
        repeat(6) {
            scope.launch {
                log("coroutine $it start")
                delay(100L)
                log("coroutine $it end")
            }
        }
    }

    private fun onDR2() {
        val scope = CoroutineScope(Dispatchers.IO)
        repeat(6) {
            scope.launch {
                log("coroutine $it start")
                delay(100L)
                log("coroutine $it end")
            }
        }
    }

    private fun onDR3() {
        val scope = CoroutineScope(Executors.newSingleThreadExecutor().asCoroutineDispatcher())
        repeat(6) {
            scope.launch {
                log("coroutine $it start")
                TimeUnit.MILLISECONDS.sleep(100)
                log("coroutine $it end")
            }
        }
    }

    private fun onDR4() {
        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            val data = getData()
            updateUI(data)
        }
    }

    private fun onDR5() {
        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            log("coroutine start")
            val data = getData()
            log("coroutine end")
        }
    }

    private fun onDR6() {
        val scope = CoroutineScope(Dispatchers.Unconfined)
        scope.launch {
            log("coroutine start")
            val data = getData()
            log("coroutine end")
        }
    }


    private suspend fun getData(): String =
        suspendCancellableCoroutine {
            log("suspend function start")
            thread {
                log("suspend function background work")
                TimeUnit.MILLISECONDS.sleep(1000)
                it.resume("Data")
            }
        }

    fun updateUI(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Column {
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
            Text(text = "Lesson 11")
            Row {
                Button(onClick = { onDR1() }) {
                    Text("onRun")
                }
                Button(onClick = { onDR2() }) {
                    Text("onRun1")
                }
                Button(onClick = { onDR3() }) {
                    Text("onRun2")
                }
                Button(onClick = { onDR4() }) {
                    Text("onRun3")
                }
            }
            Text(text = "Lesson 11.1")
            Row {
                Button(onClick = { onDR5() }) {
                    Text(text = "onRun5")
                }
                Button(onClick = { onDR6() }) {
                    Text(text = "onRun6")
                }
            }
        }
    }

    private fun log(text: String) {
        Log.d("TAG", "${formatter.format(Date())} $text [${Thread.currentThread().name}]")
    }

    private fun contextToString(context: CoroutineContext): String =
        "Job = ${context[Job]}, Dispatcher = ${context[ContinuationInterceptor]}"
}


