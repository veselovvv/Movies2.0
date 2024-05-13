package com.veselovvv.movies20.movies.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.veselovvv.movies20.movies.data.cloud.MoviesCloudDataSource
import com.veselovvv.movies20.movies.data.cloud.MoviesCloudMapper

class MoviesPagingSource(
    private val cloudDataSource: MoviesCloudDataSource,
    private val cloudMapper: MoviesCloudMapper
) : PagingSource<Int, MovieData>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieData> = try {
        val currentPage = params.key ?: 1
        val movies = cloudDataSource.fetchMovies(currentPage)
        val moviesCloudList = movies.getMoviesList()
        val moviesDataList = cloudMapper.map(moviesCloudList)

        LoadResult.Page(
            data = moviesDataList,
            prevKey = if (currentPage == 1) null else currentPage - 1,
            nextKey = if (moviesDataList.isEmpty()) null else currentPage.plus(1)
        )
    } catch (e: Exception) {
        LoadResult.Error(e)
    }

    override fun getRefreshKey(state: PagingState<Int, MovieData>): Int? =
        state.anchorPosition
}