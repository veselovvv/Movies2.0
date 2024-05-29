package com.veselovvv.movies20.movies.presentation

data class MovieParameters(
    private val id: Int,
    private val posterPath: String,
    private val releaseDate: String,
    private val title: String
) {
    fun getId() = id
    fun getPosterPath() = posterPath
    fun getReleaseDate() = releaseDate
    fun getTitle() = title
}
