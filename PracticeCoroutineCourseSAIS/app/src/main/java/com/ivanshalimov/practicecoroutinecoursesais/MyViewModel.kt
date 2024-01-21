package com.ivanshalimov.practicecoroutinecoursesais

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class MyViewModel: ViewModel() {

    private var formatter = SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault())

   /* init {
        log(formatter, "launch")
        viewModelScope.launch {
            while (true) {
                delay(1000)
                log(formatter, "work")
            }
        }
    }*/

    fun test() {
        log(formatter, "before")
        viewModelScope.launch { //CloseableCoroutineScope(SupervisorJob() + Dispatchers.Main.immediate))
            log(formatter,"launch")
        }
        log(formatter, "after")
    }

    fun test1() {
        val scope = CoroutineScope(Job()+ Dispatchers.Main)
        log(formatter, "test1 before")
        scope.launch { //CloseableCoroutineScope(SupervisorJob() + Dispatchers.Main.immediate))
            log(formatter,"test1 launch")
        }
        log(formatter, "test1 after")
    }

}