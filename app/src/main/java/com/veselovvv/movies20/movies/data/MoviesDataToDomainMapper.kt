package com.veselovvv.movies20.movies.data

import androidx.paging.PagingData
import com.veselovvv.movies20.movies.domain.MovieDomain
import kotlinx.coroutines.flow.Flow

interface MoviesDataToDomainMapper {
    fun map(movies: Flow<PagingData<MovieData>>): Flow<PagingData<MovieDomain>>
}