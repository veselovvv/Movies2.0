package com.veselovvv.movies20.movie_info.domain

import com.veselovvv.movies20.movie_info.data.MovieInfoRepository
import com.veselovvv.movies20.movie_info.data.MoviesInfoDataToDomainMapper

interface FetchMovieInfoUseCase {
    suspend fun execute(id: Int): MoviesInfoDomain

    class Base(
        private val repository: MovieInfoRepository,
        private val mapper: MoviesInfoDataToDomainMapper
    ) : FetchMovieInfoUseCase {
        override suspend fun execute(id: Int) = repository.fetchMovieInfo(id).map(mapper)
    }
}