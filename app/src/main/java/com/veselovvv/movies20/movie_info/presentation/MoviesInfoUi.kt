package com.veselovvv.movies20.movie_info.presentation

import com.veselovvv.movies20.core.Object
import com.veselovvv.movies20.movie_info.domain.MovieInfoDomain
import com.veselovvv.movies20.movie_info.domain.MovieInfoDomainToUiMapper

sealed class MoviesInfoUi : Object<Unit, MoviesInfoCommunication> {
    data class Success(
        private val movieInfo: MovieInfoDomain,
        private val movieInfoMapper: MovieInfoDomainToUiMapper
    ) : MoviesInfoUi() {
        override fun map(mapper: MoviesInfoCommunication) {
            val movieInfoUi = movieInfo.map(movieInfoMapper)
            mapper.map(movieInfoUi)
        }
    }

    data class Fail(private val message: String) : MoviesInfoUi() {
        override fun map(mapper: MoviesInfoCommunication) =
            mapper.map(MovieInfoUi.Fail(message))
    }
}