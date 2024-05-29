package com.veselovvv.movies20.movie_info.data.cloud

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

interface MovieInfoCloudDataSource {
    suspend fun fetchMovieInfo(id: Int): MovieInfoCloud

    class Base(
        private val service: MovieInfoService,
        private val gson: Gson
    ) : MovieInfoCloudDataSource {
        private val type = object : TypeToken<MovieInfoCloud>() {}.type

        override suspend fun fetchMovieInfo(id: Int): MovieInfoCloud {
            return gson.fromJson(service.fetchMovieInfo(id).string(), type)
        }
    }
}