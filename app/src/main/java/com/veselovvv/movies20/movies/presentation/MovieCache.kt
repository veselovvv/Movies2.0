package com.veselovvv.movies20.movies.presentation

import android.content.Context

interface MovieCache {
    interface Save {
        fun save(data: MovieParameters)
    }

    interface Read {
        fun read(): MovieParameters
    }

    interface Mutable : Save, Read

    class Base(context: Context) : Mutable {
        private val sharedPreferences =
            context.getSharedPreferences(MOVIE_DATA_FILENAME, Context.MODE_PRIVATE)

        override fun save(data: MovieParameters) =
            sharedPreferences.edit()
                .putInt(MOVIE_ID_KEY, data.getId())
                .putString(MOVIE_POSTER_PATH_KEY, data.getPosterPath())
                .putString(MOVIE_RELEASE_DATE_KEY, data.getReleaseDate())
                .putString(MOVIE_TITLE_KEY, data.getTitle())
                .apply()

        override fun read() = MovieParameters(
            sharedPreferences.getInt(MOVIE_ID_KEY, 0),
            sharedPreferences.getString(MOVIE_POSTER_PATH_KEY, "") ?: "",
            sharedPreferences.getString(MOVIE_RELEASE_DATE_KEY, "") ?: "",
            sharedPreferences.getString(MOVIE_TITLE_KEY, "") ?: ""
        )

        companion object {
            private const val MOVIE_DATA_FILENAME = "movieData"
            private const val MOVIE_ID_KEY = "movieIdKey"
            private const val MOVIE_POSTER_PATH_KEY = "moviePosterPathKey"
            private const val MOVIE_RELEASE_DATE_KEY = "movieReleaseDateKey"
            private const val MOVIE_TITLE_KEY = "movieTitleKey"
        }
    }
}