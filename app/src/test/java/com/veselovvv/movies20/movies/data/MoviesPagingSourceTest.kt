package com.veselovvv.movies20.movies.data

import androidx.paging.PagingSource
import com.veselovvv.movies20.core.Order
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class MoviesPagingSourceTest {
    private lateinit var order: Order
    private lateinit var cloudDataSource: FakeMoviesCloudDataSource
    private lateinit var cloudMapper: FakeMoviesCloudMapper
    private lateinit var pagingSource: MoviesPagingSource

    @Before
    fun setup() {
        order = Order()
        cloudDataSource = FakeMoviesCloudDataSource.Base(order)
        cloudMapper = FakeMoviesCloudMapper.Base(order)
        pagingSource = MoviesPagingSource(
            cloudDataSource = cloudDataSource,
            cloudMapper = cloudMapper
        )
    }

    @Test
    fun test_load_first_page() = runBlocking {
        val moviesCloudList = cloudDataSource.fetchMovies(1).getMoviesList()
        val moviesDataList = cloudMapper.map(moviesCloudList)

        val expected = PagingSource.LoadResult.Page(
            data = moviesDataList,
            prevKey = null,
            nextKey = 2
        )

        val actual = pagingSource.load(
            PagingSource.LoadParams.Append(1, 10, false)
        )

        assertEquals(expected, actual)
    }

    @Test
    fun test_load_second_page() = runBlocking {
        val moviesCloudList = cloudDataSource.fetchMovies(2).getMoviesList()
        val moviesDataList = cloudMapper.map(moviesCloudList)

        val expected = PagingSource.LoadResult.Page(
            data = moviesDataList,
            prevKey = 1,
            nextKey = 3
        )

        val actual = pagingSource.load(
            PagingSource.LoadParams.Append(2, 10, false)
        )

        assertEquals(expected, actual)
    }
}