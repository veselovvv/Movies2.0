package com.veselovvv.movies20.movies.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.veselovvv.movies20.core.Order
import org.junit.Assert.assertEquals

interface FakeMoviesCommunication : MoviesCommunication {
    companion object {
        const val MAP = "FakeMoviesCommunication#map"
    }

    fun checkList(list: List<MovieUi>)
    fun checkMapCalledCount(count: Int)

    class Base(private val order: Order) : FakeMoviesCommunication {
        private var movies = listOf<MovieUi>()
        private var mapCalledCount = 0

        override fun checkList(list: List<MovieUi>) {
            assertEquals(list, movies)
        }

        override fun checkMapCalledCount(count: Int) {
            assertEquals(count, mapCalledCount)
        }

        override fun map(movies: List<MovieUi>) {
            this.movies = movies
            mapCalledCount++
            order.add(MAP)
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<List<MovieUi>>) = Unit
    }
}