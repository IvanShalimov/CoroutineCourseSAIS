package com.ivanshalimov.practicecoroutinecoursesais

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivanshalimov.practicecoroutinecoursesais.database.AppDatabase
import com.ivanshalimov.practicecoroutinecoursesais.network.FreeService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Locale


class MyViewModel: ViewModel() {

    private var formatter = SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault())

    var retrofit = Retrofit.Builder()
        .baseUrl("https://www.boredapi.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    var service: FreeService = retrofit.create(FreeService::class.java)



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

    fun fetchData() {
        // fetch data from retrofit
        viewModelScope.launch {
            try {
                val response = service.getResponse()
                log(formatter, "$response")
            } catch (e: Exception) {
                log(formatter, "exception: $e")
            }

        }
    }

    fun initDb(db: AppDatabase) {
        viewModelScope.launch {
            val commentsDao = db.comments().getAll()
        }

    }

}