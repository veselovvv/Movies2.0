package com.veselovvv.movies20.movies.domain

import androidx.paging.PagingData
import com.veselovvv.movies20.movies.presentation.MovieUi
import kotlinx.coroutines.flow.Flow

interface MoviesDomainToUiMapper {
    fun map(movies: Flow<PagingData<MovieDomain>>): Flow<PagingData<MovieUi>>
}