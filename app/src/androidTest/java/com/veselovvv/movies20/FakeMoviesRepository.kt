package com.veselovvv.movies20

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.veselovvv.movies20.movies.data.MovieData
import com.veselovvv.movies20.movies.data.MoviesPagingSource
import com.veselovvv.movies20.movies.data.MoviesRepository
import com.veselovvv.movies20.movies.data.ToMovieMapper
import com.veselovvv.movies20.movies.data.cloud.MovieCloud
import com.veselovvv.movies20.movies.data.cloud.MoviesCloud
import com.veselovvv.movies20.movies.data.cloud.MoviesCloudDataSource
import com.veselovvv.movies20.movies.data.cloud.MoviesCloudMapper
import kotlinx.coroutines.flow.Flow

class FakeMoviesRepository : MoviesRepository {
    override suspend fun fetchMovies(): Flow<PagingData<MovieData>> {

        return Pager(
            config = PagingConfig(pageSize = MAX_PAGE_SIZE, prefetchDistance = 2),
            pagingSourceFactory = {
                MoviesPagingSource(
                    object : MoviesCloudDataSource {
                        override suspend fun fetchMovies(page: Int) =
                            MoviesCloud(
                                listOf(
                                    MovieCloud(0, "", "1985", "Back to the Future"),
                                    MovieCloud(1, "", "2002", "Eight Crazy Nights"),
                                    MovieCloud(2, "", "2003", "Kill Bill: Vol. 1")
                                )
                            )
                    },
                    MoviesCloudMapper.Base(ToMovieMapper.Base())
                )
            }
        ).flow
    }

    companion object {
        private const val MAX_PAGE_SIZE = 10
    }
}
