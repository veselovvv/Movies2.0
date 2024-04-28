package com.veselovvv.movies20.movies.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.veselovvv.movies20.databinding.FailFullscreenBinding
import com.veselovvv.movies20.databinding.MovieLayoutBinding
import com.veselovvv.movies20.databinding.NoResultsScreenBinding
import com.veselovvv.movies20.databinding.ProgressFullscreenBinding

class MoviesAdapter(
    private val retry: () -> Unit,
    private val movieListener: MovieListener
) : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {
    private val movies = ArrayList<MovieUi>()

    fun update(newList: List<MovieUi>) {
        movies.clear()
        movies.addAll(newList)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int) = when (movies[position]) {
        is MovieUi.Progress -> -1
        is MovieUi.Base -> 0
        is MovieUi.Fail -> 1
        else -> 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        -1 -> MoviesViewHolder.NoResults(
            NoResultsScreenBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
        0 -> MoviesViewHolder.Base(
            MovieLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            movieListener
        )
        1 -> MoviesViewHolder.Fail(
            FailFullscreenBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            retry
        )
        else -> MoviesViewHolder.FullscreenProgress(
            ProgressFullscreenBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) =
        holder.bind(movies[position])

    override fun getItemCount() = movies.size

    abstract class MoviesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        open fun bind(movie: MovieUi) = Unit

        class FullscreenProgress(binding: ProgressFullscreenBinding) : MoviesViewHolder(binding.root)

        class NoResults(binding: NoResultsScreenBinding) : MoviesViewHolder(binding.root)

        class Base(
            private val binding: MovieLayoutBinding,
            private val movieListener: MovieListener
        ) : MoviesViewHolder(binding.root) {
            override fun bind(movie: MovieUi) {
                movie.map(object : MovieUi.BaseMapper {
                    override fun map(posterPath: String, releaseDate: String, title: String) {
                        Glide.with(itemView)
                            .load(POSTER_BASE_URL + posterPath)
                            .into(binding.moviePoster)

                        binding.titleTextView.text = title
                        binding.releaseYearTextView.text = releaseDate.substring(0, 4)
                    }

                    override fun map(errorMessage: String) = Unit
                })

                itemView.setOnClickListener {
                    movie.open(movieListener)
                }
            }

            companion object {
                private const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342"
            }
        }

        class Fail(
            private val binding: FailFullscreenBinding,
            private val retry: () -> Unit
        ) : MoviesViewHolder(binding.root) {
            override fun bind(movie: MovieUi) {
                movie.map(object : MovieUi.BaseMapper {
                    override fun map(errorMessage: String) {
                        binding.failMessageTextView.text = errorMessage
                    }

                    override fun map(posterPath: String, releaseDate: String, title: String) = Unit
                })

                binding.failTryAgainButton.setOnClickListener {
                    retry.invoke()
                }
            }
        }
    }

    interface MovieListener {
        fun showMovie(id: Int, posterPath: String, releaseDate: String, title: String)
    }
}