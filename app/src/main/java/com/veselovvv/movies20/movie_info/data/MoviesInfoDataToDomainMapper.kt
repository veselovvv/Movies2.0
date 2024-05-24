package com.veselovvv.movies20.movie_info.data

import com.veselovvv.movies20.movie_info.domain.MoviesInfoDomain
import java.lang.Exception

interface MoviesInfoDataToDomainMapper {
    fun map(movieInfo: MovieInfoData): MoviesInfoDomain
    fun map(exception: Exception): MoviesInfoDomain
}