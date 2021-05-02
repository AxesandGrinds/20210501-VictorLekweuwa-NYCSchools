package com.a20210501_victorlekweuwa_nycschools

import java.lang.IllegalArgumentException

// Class used for Unit Test
class Calculator {

    fun parse(s: String): Int {

        val (a, op, b) = s.split(" ")

        return when  (op) {

            "*" -> a.toInt() * b.toInt()
            "/" -> a.toInt() / b.toInt()
            else -> throw IllegalArgumentException("Invalid Operator")

        }

    }

}
