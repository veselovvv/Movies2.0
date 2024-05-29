package com.veselovvv.movies20.movie_info.data

import com.veselovvv.movies20.movie_info.data.cloud.MovieInfoCloudDataSource
import com.veselovvv.movies20.movie_info.data.cloud.MovieInfoCloudMapper

interface MovieInfoRepository {
    suspend fun fetchMovieInfo(id: Int): MoviesInfoData

    class Base(
        private val cloudDataSource: MovieInfoCloudDataSource,
        private val cloudMapper: MovieInfoCloudMapper
    ) : MovieInfoRepository {
        override suspend fun fetchMovieInfo(id: Int) = try {
            val movieInfoCloud = cloudDataSource.fetchMovieInfo(id)
            val movieInfoData = cloudMapper.map(movieInfoCloud)
            MoviesInfoData.Success(movieInfoData)
        } catch (e: Exception) {
            MoviesInfoData.Fail(e)
        }
    }
}