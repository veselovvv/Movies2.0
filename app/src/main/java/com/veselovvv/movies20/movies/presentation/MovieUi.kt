package com.veselovvv.movies20.movies.presentation

import com.veselovvv.movies20.core.Object

data class MovieUi(
    private val id: Int,
    private val posterPath: String,
    private val releaseDate: String,
    private val title: String
) : Object<Unit, MovieUi.BaseMapper> {
    fun getId() = id
    fun titleContainsWith(query: String) = title.contains(query)

    override fun map(mapper: BaseMapper) = mapper.map(posterPath, releaseDate, title)

    fun open(movieListener: MoviesAdapter.MovieListener) =
        movieListener.showMovie(id, posterPath, releaseDate, title)

    interface BaseMapper {
        fun map(posterPath: String, releaseDate: String, title: String)
    }
}