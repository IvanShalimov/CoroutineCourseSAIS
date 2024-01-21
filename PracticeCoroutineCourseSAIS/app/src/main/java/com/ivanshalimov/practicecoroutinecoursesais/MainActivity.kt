package com.ivanshalimov.practicecoroutinecoursesais

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.room.Room
import com.ivanshalimov.practicecoroutinecoursesais.database.AppDatabase
import com.ivanshalimov.practicecoroutinecoursesais.ui.theme.PracticeCoroutineCourseSAISTheme
import java.text.SimpleDateFormat
import java.util.Locale

class MainActivity : ComponentActivity() {

    private val myViewModel by viewModels<MyViewModel>()
    private var formatter = SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault())

    val db = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java, "database-name"
    ).build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PracticeCoroutineCourseSAISTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
        myViewModel.test()
        /*lifecycleScope.launch {
            while (true) {
                delay(1000)
                log(formatter, "work")
            }
        }*/
    }

    private fun onButtonClick() {
        myViewModel.fetchData()
    }

    private fun onButtonClick1() {
        myViewModel.initDb(db)
    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Column {
            Row {
                Button(onClick = { onButtonClick() }) {
                    Text("Fetch data")
                }
                Button(onClick = { onButtonClick1() }) {
                    Text("Init database")
                }
            }
        }
    }
}
