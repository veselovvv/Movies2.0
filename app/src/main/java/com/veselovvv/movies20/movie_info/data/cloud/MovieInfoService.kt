package com.veselovvv.movies20.movie_info.data.cloud

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieInfoService {
    @GET("movie/{movie_id}")
    suspend fun fetchMovieInfo(@Path("movie_id") id: Int): ResponseBody
}