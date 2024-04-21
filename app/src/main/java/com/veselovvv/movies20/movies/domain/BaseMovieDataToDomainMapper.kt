package com.veselovvv.movies20.movies.domain

import com.veselovvv.movies20.movies.data.MovieDataToDomainMapper

class BaseMovieDataToDomainMapper : MovieDataToDomainMapper {
    override fun map(id: Int, posterPath: String, releaseDate: String, title: String) =
        MovieDomain(id, posterPath, releaseDate, title)
}