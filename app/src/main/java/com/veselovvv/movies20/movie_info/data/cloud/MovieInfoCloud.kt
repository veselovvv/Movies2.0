package com.veselovvv.movies20.movie_info.data.cloud

import com.google.gson.annotations.SerializedName
import com.veselovvv.movies20.core.Object
import com.veselovvv.movies20.movie_info.data.MovieInfoData
import com.veselovvv.movies20.movie_info.data.ToMovieInfoMapper

data class MovieInfoCloud(
    @SerializedName("budget")
    private val budget: Int,
    @SerializedName("overview")
    private val overview: String,
    @SerializedName("poster_path")
    private val posterPath: String?,
    @SerializedName("release_date")
    private val releaseDate: String,
    @SerializedName("revenue")
    private val revenue: Long,
    @SerializedName("runtime")
    private val runtime: Int,
    @SerializedName("title")
    private val title: String,
    @SerializedName("vote_average")
    private val rating: Double
) : Object<MovieInfoData, ToMovieInfoMapper> {
    override fun map(mapper: ToMovieInfoMapper) =
        mapper.map(
            budget,
            overview,
            posterPath ?: "",
            releaseDate,
            revenue,
            runtime,
            title,
            rating
        )
}