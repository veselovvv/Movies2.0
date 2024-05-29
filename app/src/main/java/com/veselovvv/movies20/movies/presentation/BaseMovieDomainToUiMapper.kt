package com.veselovvv.movies20.movies.presentation

import com.veselovvv.movies20.movies.domain.MovieDomainToUiMapper

class BaseMovieDomainToUiMapper : MovieDomainToUiMapper {
    override fun map(id: Int, posterPath: String, releaseDate: String, title: String): MovieUi {
        val releaseYear = if (releaseDate.isEmpty()) UNKNOWN_YEAR else releaseDate.substring(0, 4)
        return MovieUi(id, posterPath, releaseYear, title)
    }

    companion object {
        private const val UNKNOWN_YEAR = "Unknown"
    }
}