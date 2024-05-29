package com.veselovvv.movies20.core

import org.junit.Assert.assertEquals

class Order {
    private val list = mutableListOf<String>()

    fun add(name: String) {
        list.add(name)
    }

    fun check(expected: List<String>) {
        assertEquals(expected, list)
    }
}