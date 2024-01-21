package com.ivanshalimov.coroutinecoursesais

import android.os.Bundle
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
import com.ivanshalimov.coroutinecoursesais.ui.theme.CoroutineCourseSAISTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class ForthActivity : ComponentActivity() {

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

    private val scope = CoroutineScope(Job())

    private fun onFlowRun() {
        val flowList = listOf(1,2,3).asFlow()
        val flowList1 = flowOf("a","b","c")
        val flowUserData = ::getUserData.asFlow()

        scope.launch {
            flowList.collect { number ->
                log(formatter,"number: $number")
            }
            flowList1.collect { character ->
                log(formatter, "character: $character")
            }
            flowUserData.collect { userData ->
                log(formatter,"user data: ${userData.convertToString()}")
            }
        }
    }

    private fun onFlowRun1() {
        val flowMap = flowOf(1,2,3).map { it * 10 }

        val flowStrings = flow {
            emit("abc")
            emit("def")
            emit("ghi")
        }

        scope.launch {
            flowMap.collect { number ->
                log(formatter, "number is $number")
            }

            flowStrings.toUpperCase().collect { text ->
                log(formatter, text)
            }
        }
    }

    private fun onFlowRun2() {
        val flow = flowOf("a","b","c")
        scope.launch {
            log(formatter, "count: ${flow.count()}")
        }
    }

    private fun UserData.convertToString(): String {
        return "id is $id, name is $name, age is 17"
    }

    private fun Flow<String>.toUpperCase(): Flow<String> = flow {
        collect {
            emit(it.uppercase(Locale.ROOT))
        }
    }

    private fun onERun() {
        val flow = flow {
            delay(500)
            emit("1")
            delay(500)
            emit("2")

            val a = 1 / 0

            delay(500)
            emit("3")
            delay(500)
            emit("4")
        }

        scope.launch {
            flow
                .catch {
                    log(formatter,"catch $it")
                }
                .collect {
                    log(formatter,"collect $it")
                }
        }
    }

    private fun getUserData() = UserData(1,"Alex", 17)

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Column {
            Text(text = "Lesson ?")
            Row {
                Button(onClick = { onFlowRun() }) {
                    Text("onR")
                }
                Button(onClick = { onFlowRun1() }) {
                    Text("onR1")
                }
                Button(onClick = { onFlowRun2() }) {
                    Text("onR2")
                }
            }
            Text(text = "Lesson 22")
            Row {
                Button(onClick = { onERun() }) {
                    Text("onE")
                }
            }
        }
    }
}
