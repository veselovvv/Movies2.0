package com.veselovvv.movies20.movies.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.veselovvv.movies20.R
import com.veselovvv.movies20.core.presentation.BaseFragment
import com.veselovvv.movies20.core.presentation.SearchListener
import com.veselovvv.movies20.databinding.FragmentMoviesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment : BaseFragment<FragmentMoviesBinding>() {
    private val viewModel: MoviesViewModel by viewModels()
    private lateinit var toolbar: Toolbar

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentMoviesBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = MoviesAdapter(
            object : MoviesAdapter.MovieListener {
                override fun showMovie(
                    id: Int,
                    posterPath: String,
                    releaseDate: String,
                    title: String
                ) {
                    viewModel.saveMovieInfo(id, posterPath, releaseDate, title)
                    navigate(R.id.movieInfoFragment)
                }
            })

        toolbar = binding.moviesToolbar
        with(toolbar) {
            title = getString(R.string.movies)
            inflateMenu(R.menu.movies_toolbar_menu)

            setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_search_movies -> {
                        (item.actionView as SearchView).apply {
                            queryHint = getString(R.string.search_movies)
                            setOnQueryTextListener(object : SearchListener() {
                                override fun find(query: String?): Boolean {
                                    viewModel.searchMovies(query.toString())
                                    return !query.isNullOrEmpty()
                                }
                            })
                        }
                        true
                    }

                    else -> false
                }
            }
        }

        val swipeToRefreshLayout = binding.moviesSwipeToRefresh
        swipeToRefreshLayout.setOnRefreshListener {
            viewModel.fetchMovies()
            swipeToRefreshLayout.isRefreshing = false
        }

        binding.moviesRecyclerView.apply {
            this.adapter = adapter
        }

        viewModel.observe(viewLifecycleOwner) { movieUiList ->
            viewLifecycleOwner.lifecycleScope.launch {
                movieUiList.collect { pagingDataUi ->
                    adapter.submitData(viewLifecycleOwner.lifecycle, pagingDataUi)
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.collect { loadStates ->
                val state = loadStates.refresh
                binding.moviesProgressBar.isVisible = state is LoadState.Loading
            }
        }

        viewModel.fetchMovies()

        binding.moviesRecyclerView.adapter = adapter.withLoadStateFooter(
            LoadMoreAdapter {
                adapter.retry()
            }
        )
    }
}