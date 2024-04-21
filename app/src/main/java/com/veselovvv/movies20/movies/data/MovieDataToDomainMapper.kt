package com.veselovvv.movies20.movies.data

interface MovieDataToDomainMapper {
    fun map(
        id: Int,
        posterPath: String,
        releaseDate: String,
        title: String
    ): MovieDomain
}