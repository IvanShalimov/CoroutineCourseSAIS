package com.ivanshalimov.practicecoroutinecoursesais

import kotlinx.coroutines.delay

class MyClass {

    suspend fun someMethod(): String {
        return "abc"
    }

    suspend fun someMethod1(): String {
        delay(5000)
        return "abc"
    }
}