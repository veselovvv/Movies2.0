package com.veselovvv.movies20.movies.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.veselovvv.movies20.databinding.MovieLayoutBinding

class MoviesAdapter(
    private val movieListener: MovieListener
) : PagingDataAdapter<MovieUi, MoviesAdapter.MoviesViewHolder>(differCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MoviesViewHolder(
            MovieLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            movieListener
        )

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        getItem(position)?.let { movie ->
            holder.bind(movie)
        }
    }

    class MoviesViewHolder(
        private val binding: MovieLayoutBinding,
        private val movieListener: MovieListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieUi) {
            movie.map(object : MovieUi.BaseMapper {
                override fun map(posterPath: String, releaseDate: String, title: String) {
                    if (posterPath.isNotEmpty()) {
                        Glide.with(itemView)
                            .load(POSTER_BASE_URL + posterPath)
                            .into(binding.moviePoster)
                    }

                    binding.titleTextView.text = title
                    binding.releaseYearTextView.text = releaseDate
                }
            })

            itemView.setOnClickListener {
                movie.open(movieListener)
            }
        }

        companion object {
            private const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342"
        }
    }

    interface MovieListener {
        fun showMovie(id: Int, posterPath: String, releaseDate: String, title: String)
    }

    companion object {
        private val differCallback = object : DiffUtil.ItemCallback<MovieUi>() {
            override fun areItemsTheSame(oldItem: MovieUi, newItem: MovieUi): Boolean {
                return oldItem.getId() == newItem.getId()
            }

            override fun areContentsTheSame(oldItem: MovieUi, newItem: MovieUi): Boolean {
                return oldItem == newItem
            }
        }
    }
}