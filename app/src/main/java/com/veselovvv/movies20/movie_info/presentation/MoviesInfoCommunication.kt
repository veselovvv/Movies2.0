package com.veselovvv.movies20.movie_info.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

interface MoviesInfoCommunication {
    fun map(movieInfo: MovieInfoUi)
    fun observe(owner: LifecycleOwner, observer: Observer<MovieInfoUi>)

    class Base : MoviesInfoCommunication {
        private val movieInfoLiveData = MutableLiveData<MovieInfoUi>()

        override fun map(movieInfo: MovieInfoUi) {
            movieInfoLiveData.value = movieInfo
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<MovieInfoUi>) =
            movieInfoLiveData.observe(owner, observer)
    }
}