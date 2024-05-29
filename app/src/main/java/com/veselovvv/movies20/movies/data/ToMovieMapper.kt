package com.veselovvv.movies20.movies.data

interface ToMovieMapper {
    fun map(id: Int, posterPath: String, releaseDate: String, title: String): MovieData

    class Base : ToMovieMapper {
        override fun map(
            id: Int,
            posterPath: String,
            releaseDate: String,
            title: String
        ) = MovieData(id, posterPath, releaseDate, title)
    }
}