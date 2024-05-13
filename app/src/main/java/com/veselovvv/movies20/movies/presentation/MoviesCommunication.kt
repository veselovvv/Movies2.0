package com.veselovvv.movies20.movies.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

interface MoviesCommunication {
    suspend fun map(movies: Flow<PagingData<MovieUi>>)
    fun observe(owner: LifecycleOwner, observer: Observer<Flow<PagingData<MovieUi>>>)

    class Base : MoviesCommunication {
        private val moviesState = MutableStateFlow<PagingData<MovieUi>>(PagingData.empty())

        override suspend fun map(movies: Flow<PagingData<MovieUi>>) {
            movies.collect { pagingData ->
                moviesState.value = pagingData
            }
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<Flow<PagingData<MovieUi>>>) {
            val flow = moviesState.asStateFlow()
            observer.onChanged(flow)
        }
    }
}