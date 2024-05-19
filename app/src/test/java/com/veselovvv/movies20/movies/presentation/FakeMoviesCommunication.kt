package com.veselovvv.movies20.movies.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.paging.PagingData
import androidx.paging.map
import com.veselovvv.movies20.core.Order
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.junit.Assert.assertEquals

interface FakeMoviesCommunication : MoviesCommunication {
    companion object {
        const val MOVIES_COMMUNICATION_MAP = "FakeMoviesCommunication#map"
    }

    fun checkList(list: List<MovieUi>)
    fun checkMapCalledCount(count: Int)

    class Base(private val order: Order) : FakeMoviesCommunication {
        private var movies = mutableListOf<MovieUi>()
        private var mapCalledCount = 0

        override fun checkList(list: List<MovieUi>) {
            assertEquals(list, movies)
        }

        override fun checkMapCalledCount(count: Int) {
            assertEquals(count, mapCalledCount)
        }

        override suspend fun map(movies: Flow<PagingData<MovieUi>>) {
            movies.map { pagingData ->
                pagingData.map { movieUi ->
                    this.movies.add(movieUi)
                }
            }

            mapCalledCount++
            order.add(MOVIES_COMMUNICATION_MAP)
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<Flow<PagingData<MovieUi>>>) = Unit
    }
}
