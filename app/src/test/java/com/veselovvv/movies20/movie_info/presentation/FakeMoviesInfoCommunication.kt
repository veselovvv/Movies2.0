package com.veselovvv.movies20.movie_info.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.veselovvv.movies20.core.Order
import junit.framework.Assert.assertEquals

interface FakeMoviesInfoCommunication : MoviesInfoCommunication {
    companion object {
        const val MOVIES_INFO_COMMUNICATION_MAP = "FakeMoviesInfoCommunication#map"
    }

    fun checkMovieInfo(movieInfo: MovieInfoUi)
    fun checkMapCalledCount(count: Int)

    class Base(private val order: Order) : FakeMoviesInfoCommunication {
        private var movieInfo: MovieInfoUi = MovieInfoUi.Progress
        private var mapCalledCount = 0

        override fun checkMovieInfo(movieInfo: MovieInfoUi) {
            assertEquals(movieInfo, this.movieInfo)
        }

        override fun checkMapCalledCount(count: Int) {
            assertEquals(count, mapCalledCount)
        }

        override fun map(movieInfo: MovieInfoUi) {
            mapCalledCount++
            order.add(MOVIES_INFO_COMMUNICATION_MAP)
            this.movieInfo = movieInfo
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<MovieInfoUi>) = Unit
    }
}