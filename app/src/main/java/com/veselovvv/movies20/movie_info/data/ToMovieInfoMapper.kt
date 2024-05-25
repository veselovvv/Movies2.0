package com.veselovvv.movies20.movie_info.data

interface ToMovieInfoMapper {
    fun map(
        budget: Int,
        overview: String,
        posterPath: String,
        releaseDate: String,
        revenue: Long,
        runtime: Int,
        title: String,
        rating: Double
    ): MovieInfoData

    class Base : ToMovieInfoMapper {
        override fun map(
            budget: Int,
            overview: String,
            posterPath: String,
            releaseDate: String,
            revenue: Long,
            runtime: Int,
            title: String,
            rating: Double
        ) = MovieInfoData(
            budget, overview, posterPath, releaseDate, revenue, runtime, title, rating
        )
    }
}