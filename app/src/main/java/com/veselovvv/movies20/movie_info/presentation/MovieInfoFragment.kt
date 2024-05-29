package com.veselovvv.movies20.movie_info.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.veselovvv.movies20.core.presentation.BaseFragment
import com.veselovvv.movies20.databinding.FragmentMovieInfoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieInfoFragment : BaseFragment<FragmentMovieInfoBinding>() {
    private val viewModel: MoviesInfoViewModel by viewModels()

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentMovieInfoBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val swipeToRefreshLayout = binding.movieInfoSwipeToRefresh
        swipeToRefreshLayout.setOnRefreshListener {
            viewModel.fetchMovieInfo(viewModel.getMovieId())
            swipeToRefreshLayout.isRefreshing = false
        }

        viewModel.observe(this) { ui ->
            ui.setup(binding.movieInfoProgressLayout.root)
            ui.map(
                binding.movieInfoBudget,
                binding.movieInfoOverview,
                binding.movieInfoPoster,
                binding.movieInfoReleaseDate,
                binding.movieInfoRevenue,
                binding.movieInfoRuntime,
                binding.movieInfoTitle,
                binding.movieInfoRating
            )
            ui.map(
                binding.movieInfoFailLayout.root,
                binding.movieInfoFailLayout.failMessageTextView,
                binding.movieInfoFailLayout.failTryAgainButton
            ) {
                viewModel.fetchMovieInfo(viewModel.getMovieId())
            }
        }
        viewModel.fetchMovieInfo(viewModel.getMovieId())
    }
}