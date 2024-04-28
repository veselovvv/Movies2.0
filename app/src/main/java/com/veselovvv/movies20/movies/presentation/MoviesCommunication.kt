package com.veselovvv.movies20.movies.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

interface MoviesCommunication {
    fun map(movies: List<MovieUi>)
    fun observe(owner: LifecycleOwner, observer: Observer<List<MovieUi>>)

    class Base : MoviesCommunication {
        private val moviesLiveData = MutableLiveData<List<MovieUi>>()

        override fun map(movies: List<MovieUi>) {
            moviesLiveData.value = movies
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<List<MovieUi>>) =
            moviesLiveData.observe(owner, observer)
    }
}