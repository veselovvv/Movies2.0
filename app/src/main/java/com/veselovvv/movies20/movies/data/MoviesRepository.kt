package com.veselovvv.movies20.movies.data

import com.veselovvv.movies20.movies.data.cloud.MoviesCloudDataSource
import com.veselovvv.movies20.movies.data.cloud.MoviesCloudMapper

interface MoviesRepository {
    suspend fun fetchMovies(): MoviesData
    suspend fun searchMovies(query: String): MoviesData

    class Base(
        private val cloudDataSource: MoviesCloudDataSource,
        private val cloudMapper: MoviesCloudMapper
    ) : MoviesRepository {
        override suspend fun fetchMovies() = try {
            val moviesCloudList = cloudDataSource.fetchMovies()
            val moviesDataList = cloudMapper.map(moviesCloudList)
            MoviesData.Success(moviesDataList)
        } catch (e: Exception) {
            MoviesData.Fail(e)
        }

        override suspend fun searchMovies(query: String) = try {
            val moviesCloudList = cloudDataSource.fetchMovies().filter { movie ->
                movie.titleStartsWith(query)
            }

            val moviesDataList = cloudMapper.map(moviesCloudList)
            MoviesData.Success(moviesDataList)
        } catch (e: Exception) {
            MoviesData.Fail(e)
        }
    }
}