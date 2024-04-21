package com.veselovvv.movies20.movies.data.cloud

import okhttp3.ResponseBody
import retrofit2.http.GET

interface MoviesService {
    @GET("movie/popular")
    suspend fun fetchMovies(): ResponseBody
}