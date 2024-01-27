package com.ivanshalimov.practicecoroutinecoursesais

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
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ivanshalimov.practicecoroutinecoursesais.ui.theme.PracticeCoroutineCourseSAISTheme
import java.text.SimpleDateFormat
import java.util.Locale

class MainActivity3 : ComponentActivity() {

    private var formatter = SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault())
    var i = mutableIntStateOf(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PracticeCoroutineCourseSAISTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(i)
                }
            }
        }
    }


    @Composable
    fun Greeting(text: State<Int>) {
        log(formatter, "new value is ${text.value}")
        Column {
            Row {
                Button( onClick = { onClick() }) {
                    Text(text = "${text.value}")
                }
            }
        }
    }

    private fun onClick() {
        i.intValue++
    }
}


