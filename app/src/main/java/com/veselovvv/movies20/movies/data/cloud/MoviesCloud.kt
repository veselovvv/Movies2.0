package com.veselovvv.movies20.movies.data.cloud

import com.google.gson.annotations.SerializedName

data class MoviesCloud(
    @SerializedName("results")
    private val movies: List<MovieCloud>
) {
    fun getMoviesList() = movies
}
