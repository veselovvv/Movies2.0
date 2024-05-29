package com.veselovvv.movies20.movie_info.presentation

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView

sealed class MovieInfoUi {
    open fun setup(progressLayout: ViewGroup) {
        progressLayout.visibility = View.GONE
    }

    open fun map(
        budgetTextView: MaterialTextView,
        overviewTextView: MaterialTextView,
        posterImageView: ImageView,
        releaseDateTextView: MaterialTextView,
        revenueTextView: MaterialTextView,
        runtimeTextView: MaterialTextView,
        titleTextView: MaterialTextView,
        ratingTextView: MaterialTextView
    ) = Unit

    open fun map(
        failLayout: ViewGroup,
        messageTextView: MaterialTextView,
        tryAgainButton: MaterialButton,
        retry: () -> Unit
    ) = Unit

    object Progress : MovieInfoUi() {
        override fun setup(progressLayout: ViewGroup) {
            progressLayout.visibility = View.VISIBLE
        }
    }

    data class Base(
        private val budget: String,
        private val overview: String,
        private val posterPath: String,
        private val releaseDate: String,
        private val revenue: String,
        private val runtime: String,
        private val title: String,
        private val rating: String
    ) : MovieInfoUi() {
        override fun map(
            budgetTextView: MaterialTextView,
            overviewTextView: MaterialTextView,
            posterImageView: ImageView,
            releaseDateTextView: MaterialTextView,
            revenueTextView: MaterialTextView,
            runtimeTextView: MaterialTextView,
            titleTextView: MaterialTextView,
            ratingTextView: MaterialTextView
        ) {
            Glide.with(posterImageView)
                .load(POSTER_BASE_URL + posterPath)
                .into(posterImageView)

            budgetTextView.text = budget
            overviewTextView.text = overview
            releaseDateTextView.text = releaseDate
            revenueTextView.text = revenue
            runtimeTextView.text = runtime
            titleTextView.text = title
            ratingTextView.text = rating
        }

        companion object {
            private const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342"
        }
    }

    data class Fail(private val message: String) : MovieInfoUi() {
        override fun map(
            failLayout: ViewGroup,
            messageTextView: MaterialTextView,
            tryAgainButton: MaterialButton,
            retry: () -> Unit
        ) {
            failLayout.visibility = View.VISIBLE
            messageTextView.text = message
            tryAgainButton.setOnClickListener {
                retry()
                failLayout.visibility = View.GONE
            }
        }
    }
}