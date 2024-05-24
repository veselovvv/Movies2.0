package com.veselovvv.movies20.movie_info.data

interface MovieInfoRepository {
    suspend fun fetchMovieInfo(id: Int): MoviesInfoData

    class Base : MovieInfoRepository {
        override suspend fun fetchMovieInfo(id: Int): MoviesInfoData {
            TODO("Not yet implemented")
        }
    }
}