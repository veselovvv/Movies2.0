package com.veselovvv.movies20.movies.data.cloud

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService {
    @GET("movie/popular")
    suspend fun fetchMovies(@Query("page") page: Int): ResponseBody
}