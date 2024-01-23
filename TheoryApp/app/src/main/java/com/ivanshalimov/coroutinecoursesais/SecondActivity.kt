package com.ivanshalimov.coroutinecoursesais

import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
import com.ivanshalimov.coroutinecoursesais.models.UserData
import com.ivanshalimov.coroutinecoursesais.ui.theme.CoroutineCourseSAISTheme
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
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

    private fun onC1() {
        val scope = CoroutineScope(Job() + Dispatchers.IO)
        val job = scope.launch {
            log("parent start")
            launch {
                log("child start")
                delay(1000)
                log("child end")
            }
            log("parent end")
        }

        scope.launch {
            delay(500)
            log("parent job is active: ${job.isActive}")
            delay(1000)
            log("parent job is active: ${job.isActive}")
        }
    }

    private fun onEC() {
        val scope = CoroutineScope(Job() + Dispatchers.Default)
        log("onRun start")

        scope.launch {
            try {
                Integer.parseInt("a")
            } catch (e: Exception) {
                log("exception: $e")
            }
        }
        log("onRun end")
    }

    private fun onEC1() {
        val handler = CoroutineExceptionHandler {context, exception ->
            log("exception: $exception")
        }
        val scope = CoroutineScope(Job() + Dispatchers.Default + handler)
        log("onRun start")
        scope.launch {
            Integer.parseInt("a")
        }
        log("onRun end")
    }

    private fun onEC2() {
        val handler = CoroutineExceptionHandler {context, exception ->
            log("first coroutine exception: $exception")
        }
        val scope = CoroutineScope(Job() + Dispatchers.Default + handler)
        scope.launch {
            TimeUnit.MILLISECONDS.sleep(1000L)
//            try {
                Integer.parseInt("a")
/*            } catch (e: Exception) {
                log("first coroutine exception: $e")
            }*/

        }
        scope.launch {
            repeat(6) {
                TimeUnit.MILLISECONDS.sleep(300L)
                log("second coroutine isActive: $isActive")
            }
        }

    }

    private fun onEC3() {
        val handler = CoroutineExceptionHandler {context, exception ->
            log("first coroutine exception: $exception")
        }
        val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default + handler)
        scope.launch {
            TimeUnit.MILLISECONDS.sleep(1000L)
            Integer.parseInt("a")

        }
        scope.launch {
            repeat(6) {
                TimeUnit.MILLISECONDS.sleep(300L)
                log("second coroutine isActive: $isActive")
            }
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
            Text(text = "Lesson 12")
            Row {
                Button(onClick = { onC1() }) {
                    Text("onRun")
                }
            }
            Text("Lesson 13")
            Row {
                Button(onClick = { onEC() }) {
                    Text(text = "onRun")
                }
                Button(onClick = { onEC1() }) {
                    Text(text = "onRun1")
                }
                Button(onClick = { onEC2() }) {
                    Text(text = "onRun2")
                }
                Button(onClick = { onEC3() }) {
                    Text(text = "onRun3")
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


