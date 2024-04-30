package com.veselovvv.movies20.movies.presentation

import com.veselovvv.movies20.movies.domain.MovieDomainToUiMapper

class BaseMovieDomainToUiMapper : MovieDomainToUiMapper {
    override fun map(id: Int, posterPath: String, releaseDate: String, title: String): MovieUi {
        val releaseYear = releaseDate.substring(0, 4)
        return MovieUi.Base(id, posterPath, releaseYear, title)
    }
}