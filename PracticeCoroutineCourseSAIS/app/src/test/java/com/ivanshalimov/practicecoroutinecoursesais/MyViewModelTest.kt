package com.ivanshalimov.practicecoroutinecoursesais

import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test


class MyViewModelTest {

    val viewModel = MyViewModel()

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun fetchDataForTest() = runTest {
        viewModel.fetchDataForTest()
        assertTrue(viewModel.loading.value)
        testScheduler.runCurrent()
        assertFalse(viewModel.loading.value)
    }

    @Test
    fun testAdvanced() = runTest {
        viewModel.showAndHideDialog()
        assertFalse(viewModel.showDialog.value)

        testScheduler.advanceTimeBy(1001L)
        assertTrue(viewModel.showDialog.value)

        testScheduler.advanceTimeBy(3000L)
        assertFalse(viewModel.showDialog.value)
    }
}