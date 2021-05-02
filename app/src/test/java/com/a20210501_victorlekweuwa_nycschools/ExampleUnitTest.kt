package com.a20210501_victorlekweuwa_nycschools

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testMultiply() {

        val calculator = Calculator()

        val result = calculator.parse("3 * 3")
        assertEquals(9, result)

    }

    @Test
    fun testDivide() {

        val calculator = Calculator()

        val result = calculator.parse("4 / 4")
        assertEquals(1, result)

    }

}