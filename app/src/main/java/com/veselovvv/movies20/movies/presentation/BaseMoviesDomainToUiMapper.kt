package com.veselovvv.movies20.movies.presentation

import androidx.paging.PagingData
import androidx.paging.map
import com.veselovvv.movies20.movies.domain.MovieDomain
import com.veselovvv.movies20.movies.domain.MovieDomainToUiMapper
import com.veselovvv.movies20.movies.domain.MoviesDomainToUiMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BaseMoviesDomainToUiMapper(
    private val movieMapper: MovieDomainToUiMapper
) : MoviesDomainToUiMapper {
    override fun map(movies: Flow<PagingData<MovieDomain>>) = movies.map { pagingData ->
        pagingData.map { movieDomain ->
            movieDomain.map(movieMapper)
        }
    }
}