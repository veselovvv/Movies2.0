package com.veselovvv.movies20.movie_info.data.cloud

import com.veselovvv.movies20.movie_info.data.MovieInfoData
import com.veselovvv.movies20.movie_info.data.ToMovieInfoMapper

interface MovieInfoCloudMapper {
    fun map(movieInfo: MovieInfoCloud): MovieInfoData

    class Base(private val movieInfoMapper: ToMovieInfoMapper) : MovieInfoCloudMapper {
        override fun map(movieInfo: MovieInfoCloud) = movieInfo.map(movieInfoMapper)
    }
}