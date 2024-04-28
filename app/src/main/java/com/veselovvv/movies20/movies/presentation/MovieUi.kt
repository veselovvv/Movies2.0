package com.veselovvv.movies20.movies.presentation

import com.veselovvv.movies20.core.Object

sealed class MovieUi : Object<Unit, MovieUi.BaseMapper> {
    override fun map(mapper: BaseMapper) = Unit
    open fun open(movieListener: MoviesAdapter.MovieListener) = Unit

    object Progress : MovieUi()

    object NoResults : MovieUi()

    data class Base(
        private val id: Int,
        private val posterPath: String,
        private val releaseDate: String,
        private val title: String
    ) : MovieUi() {
        override fun map(mapper: BaseMapper) = mapper.map(posterPath, releaseDate, title)
        override fun open(movieListener: MoviesAdapter.MovieListener) =
            movieListener.showMovie(id, posterPath, releaseDate, title)
    }

    data class Fail(private val errorMessage: String) : MovieUi() {
        override fun map(mapper: BaseMapper) = mapper.map(errorMessage)
    }

    interface BaseMapper {
        fun map(posterPath: String, releaseDate: String, title: String)
        fun map(errorMessage: String)
    }
}