package com.ivanshalimov.practicecoroutinecoursesais

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test

class MyClassTest {

    val myClass = MyClass()

    @Test
    fun testSomeMethod() = runBlocking {
        val actualValue = myClass.someMethod()
        assertEquals("abc", actualValue)
    }

}