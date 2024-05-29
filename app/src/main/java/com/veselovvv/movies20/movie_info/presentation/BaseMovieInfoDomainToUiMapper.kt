package com.veselovvv.movies20.movie_info.presentation

import com.veselovvv.movies20.movie_info.domain.MovieInfoDomainToUiMapper
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

class BaseMovieInfoDomainToUiMapper : MovieInfoDomainToUiMapper {
    override fun map(
        budget: Int,
        overview: String,
        posterPath: String,
        releaseDate: String,
        revenue: Long,
        runtime: Int,
        title: String,
        rating: Double
    ) = MovieInfoUi.Base(
        formatPrice(budget.toString()),
        overview,
        posterPath,
        formatDate(releaseDate),
        formatPrice(revenue.toString()),
        runtime.toString(),
        title,
        rating.toString()
    )

    override fun formatPrice(value: String): String {
        val number = value.toLongOrNull() ?: return value // handle invalid input
        val symbols = DecimalFormatSymbols(Locale.getDefault()).apply {
            groupingSeparator = SEPARATOR
        }
        val formatter = DecimalFormat("#,###", symbols)
        return "$${formatter.format(number)}"
    }

    override fun formatDate(value: String): String {
        val year = value.substring(0, 4)
        val month = value.substring(5, 7)
        val day = value.substring(8, 10)

        return day + SEPARATOR + month + SEPARATOR + year
    }

    companion object {
        private const val SEPARATOR = '.'
    }
}