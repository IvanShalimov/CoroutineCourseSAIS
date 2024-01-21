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
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
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

    fun onRun2() {
        val handler = CoroutineExceptionHandler { context, exception ->
            log("$exception was handled in Coroutine_${context[CoroutineName]?.name}")
        }

        val scope = CoroutineScope(Job() + Dispatchers.Default + handler)

        scope.launch {
            log("parent context: $coroutineContext")
            supervisorScope {
                launch {
                    log("context: $coroutineContext")
                }
            }
        }
    }

    private fun onRunC() {
        val scope = CoroutineScope(Job() + Dispatchers.Default)
        val channel = Channel<Int>()

        scope.launch {
            launch {
                delay(300L)
                log("send 5")
                channel.send(5)
                log("send, done")
            }

            launch {
                delay(1000L)
                log("receive")
                val i = channel.receive()
                log("receive $i, done")
            }
        }
    }

    private fun onRunC1() {
        val scope = CoroutineScope(Job() + Dispatchers.Default)
        val channel = Channel<Int>()

        scope.launch {
            launch {
                delay(1000L)
                log("send 5")
                channel.send(5)
                log("send, done")
            }

            launch {
                delay(300L)
                log("receive")
                val i = channel.receive()
                log("receive $i, done")
            }
        }
    }

    private fun onRunC2() {
        val scope = CoroutineScope(Job() + Dispatchers.Default)
        val channel = Channel<Int>(2) // size of capacity - 2

        scope.launch {
            launch {
                repeat(7) {
                    delay(300L)
                    log("send $it")
                    channel.send(it)
                }
                log("close")
                channel.close()// close the chanel - ClosedReceiveChannelException
            }
            launch {
                for (element in channel) {
                    log("received $element")
                    delay(1000L)
                }
            }
        }
    }

    private fun onRunC3() {
        val scope = CoroutineScope(Job() + Dispatchers.Default)
        //Channel.Factory.UNLIMITED, Channel.Factory.BUFFERED(size = 64)
        val channel = Channel<Int>(Channel.Factory.CONFLATED) //No buffer, replace value

        scope.launch {
            launch {
                repeat(7) {
                    delay(300L)
                    log("send $it")
                    channel.send(it)
                }
                log("close")
                channel.close()// close the chanel - ClosedReceiveChannelException/ ClosedSendChannelException
            }
            launch {
                for (element in channel) {
                    log("received $element")
                    delay(1000L)
                }
            }
        }
    }

    private fun onRunC4() {
        val scope = CoroutineScope(Job() + Dispatchers.Default)
        val channel = Channel<Int>(2) // CancellationException

        scope.launch {
            launch {
                repeat(7) {
                    delay(300L)
                    log("send $it")
                    channel.send(it)
                }
                log("close")
                channel.cancel()// cancel the chanel, clean buffer
            }
            launch {
                for (element in channel) {
                    log("received $element")
                    delay(1000L)
                }
            }
        }
    }

    private fun onRunFlow() {
        val flow = flow {
            // flow block
            for (i in 1..10) {
                delay(100L)
                emit(i)
            }
        }

        val scope = CoroutineScope(Job())

        scope.launch {
            flow.collect { i ->
                //collect block
                log("i: $i")
            }
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
                Button(onClick = { onRun2() }) {
                    Text("onRun2")
                }
            }
            Text(text = "Lesson 18")
            Row {
                Button(onClick = { onRunC() }) {
                    Text("onRun")
                }
                Button(onClick = { onRunC1() }) {
                    Text("onRun1")
                }
                Button(onClick = { onRunC2() }) {
                    Text("onRun2")
                }
                Button(onClick = { onRunC3() }) {
                    Text("onRun3")
                }
            }
            Text(text = "Lesson 18.1")
            Row {
                Button(onClick = { onRunC4() }) {
                    Text(text = "onRun4")
                }
            }
            Text(text = "Lesson 19")
            Row {
                Button(onClick = { onRunFlow() }) {
                    Text(text = "onRun")
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
