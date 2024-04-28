package com.veselovvv.movies20.movies.presentation

import com.veselovvv.movies20.movies.domain.MovieDomainToUiMapper

class BaseMovieDomainToUiMapper : MovieDomainToUiMapper {
    override fun map(id: Int, posterPath: String, releaseDate: String, title: String) =
        MovieUi.Base(id, posterPath, releaseDate, title)
}