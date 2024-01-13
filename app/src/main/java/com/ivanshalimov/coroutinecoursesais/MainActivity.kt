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
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    val context = Job() + Dispatchers.Default
    val scope = CoroutineScope(context) // the simplest creation of coroutine
    private var formatter = SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault())
    lateinit var lazyJob: Job

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
            val job1 = launch {
                log("child coroutine1 start")
                delay(1000L)
                log("child coroutine1 end")
            }

            log("parent coroutine waits to children completes")
            job.join()
            job1.join()
            log("parent coroutine end")
        }

        lazyJob = scope.launch(start = CoroutineStart.LAZY) {
            log("lazy coroutine start")
            delay(1000L)
            log("lazy coroutine end")
        }
    }

    private fun onRun2() {
        log("onRun2 start")
        lazyJob.start()
        log("onRun2 end")
    }

    private fun onRun3() {
        scope.launch {
            log("parent coroutine start")
            val deferred = async {
                log("child coroutine start")
                delay(1000L)
                log("child coroutine end")
                "async result"
            }
            log("parent coroutine waits until child returns result")
            val result = deferred.await()
            log("parent coroutine, child result = $result")
            log("parent coroutine end")
        }
    }

    private fun onCancel() {
        log("onCancel")
        scope.cancel()
    }

    private fun onLogContext() {
        val context = Job() + Dispatchers.Default
        log("context: $context")
    }

    private fun onLogContextWithoutJob() {
        val scope = CoroutineScope(Dispatchers.Default + UserData(0L, "Ivan", 17))
        log("context = ${scope.coroutineContext}")
        log("user data: ${scope.coroutineContext[UserData]}")
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
                Button(onClick = { onRun3() }) {
                    Text("Run3")
                }
            }
            Text(text = "Lesson 10")
            Row {
                Button(onClick = { onLogContext() }) {
                    Text("LogContext")
                }
                Button(onClick = { onLogContextWithoutJob() }) {
                    Text("ContextWithoutJob")
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
