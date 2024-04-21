package com.veselovvv.movies20.movies.data.cloud

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

interface MoviesCloudDataSource {
    suspend fun fetchMovies(): List<MovieCloud>

    class Base(
        private val service: MoviesService,
        private val gson: Gson
    ) : MoviesCloudDataSource {
        private val type = object : TypeToken<MoviesCloud>() {}.type

        override suspend fun fetchMovies(): List<MovieCloud> {
            val moviesCloud: MoviesCloud = gson.fromJson(service.fetchMovies().string(), type)
            return moviesCloud.getMoviesList()
        }
    }
}