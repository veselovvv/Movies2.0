package com.veselovvv.movies20.movies.domain

import androidx.paging.PagingData
import androidx.paging.map
import com.veselovvv.movies20.movies.data.MovieData
import com.veselovvv.movies20.movies.data.MovieDataToDomainMapper
import com.veselovvv.movies20.movies.data.MoviesDataToDomainMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BaseMoviesDataToDomainMapper(
    private val movieMapper: MovieDataToDomainMapper
) : MoviesDataToDomainMapper {
    override fun map(movies: Flow<PagingData<MovieData>>) = movies.map { pagingData ->
        pagingData.map { movieData ->
            movieData.map(movieMapper)
        }
    }
}