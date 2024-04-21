package com.veselovvv.movies20.movies.data.cloud

import com.google.gson.annotations.SerializedName
import com.veselovvv.movies20.core.Object
import com.veselovvv.movies20.movies.data.MovieData
import com.veselovvv.movies20.movies.data.ToMovieMapper

data class MovieCloud(
    @SerializedName("id")
    private val id: Int,
    @SerializedName("poster_path")
    private val posterPath: String,
    @SerializedName("release_date")
    private val releaseDate: String,
    @SerializedName("title")
    private val title: String
) : Object<MovieData, ToMovieMapper> {
    fun titleStartsWith(query: String) = title.startsWith(query)

    override fun map(mapper: ToMovieMapper) = mapper.map(id, posterPath, releaseDate, title)
}
