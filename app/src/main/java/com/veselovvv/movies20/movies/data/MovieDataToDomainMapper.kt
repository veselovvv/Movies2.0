package com.veselovvv.movies20.movies.data

import com.veselovvv.movies20.movies.domain.MovieDomain

interface MovieDataToDomainMapper {
    fun map(
        id: Int,
        posterPath: String,
        releaseDate: String,
        title: String
    ): MovieDomain
}