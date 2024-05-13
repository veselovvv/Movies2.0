package com.veselovvv.movies20.movies.data.cloud

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

interface MoviesCloudDataSource {
    suspend fun fetchMovies(page: Int): MoviesCloud

    class Base(
        private val service: MoviesService,
        private val gson: Gson
    ) : MoviesCloudDataSource {
        private val type = object : TypeToken<MoviesCloud>() {}.type

        override suspend fun fetchMovies(page: Int): MoviesCloud =
            gson.fromJson(service.fetchMovies(page).string(), type)
    }
}