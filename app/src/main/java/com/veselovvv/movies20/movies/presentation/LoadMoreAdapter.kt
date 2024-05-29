package com.veselovvv.movies20.movies.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.veselovvv.movies20.R
import com.veselovvv.movies20.databinding.LoadMoreLayoutBinding

class LoadMoreAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<LoadMoreAdapter.LoadMoreViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        LoadMoreViewHolder(
            LoadMoreLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            retry
        )

    override fun onBindViewHolder(holder: LoadMoreViewHolder, loadState: LoadState) =
        holder.bind(loadState)

    inner class LoadMoreViewHolder(
        private val binding: LoadMoreLayoutBinding,
        private val retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) {
            binding.apply {
                loadMoreProgressBar.isVisible = loadState is LoadState.Loading
                errorTextView.isVisible = loadState is LoadState.Error
                retryButton.isVisible = loadState is LoadState.Error

                if (loadState is LoadState.Error) {
                    if (loadState.error.stackTraceToString().contains(HTTP_400)) {
                        retryButton.isVisible = false
                        errorTextView.text = itemView.context.getString(R.string.no_more_data_to_show)
                    } else {
                        errorTextView.text = itemView.context.getString(R.string.something_went_wrong)
                    }
                }

                retryButton.setOnClickListener {
                    retry.invoke()
                }
            }
        }
    }

    companion object {
        private const val HTTP_400 = "HTTP 400"
    }
}