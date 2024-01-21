package com.ivanshalimov.practicecoroutinecoursesais

import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date

fun log(formatter: SimpleDateFormat, text: String) {
    Log.d("TAG", "${formatter.format(Date())} $text [${Thread.currentThread().name}]")
}