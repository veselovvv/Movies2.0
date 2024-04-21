package com.veselovvv.movies20.movies.data

import com.veselovvv.movies20.core.Order
import com.veselovvv.movies20.movies.data.FakeToMovieMapper.Companion.TO_MOVIE_MAP
import com.veselovvv.movies20.movies.data.cloud.MovieCloud
import com.veselovvv.movies20.movies.data.cloud.MoviesCloudMapper
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MoviesCloudMapperTest {
    private lateinit var order: Order
    private lateinit var movieMapper: FakeToMovieMapper
    private lateinit var mapper: MoviesCloudMapper

    @Before
    fun setup() {
        order = Order()
        movieMapper = FakeToMovieMapper.Base(order)
        mapper = MoviesCloudMapper.Base(movieMapper = movieMapper)
    }

    @Test
    fun test_map_one_item() {
        val movies = listOf(
            MovieCloud(
                id = 1,
                posterPath = "somePath1",
                releaseDate = "2002-01-01",
                title = "Star Wars: Episode II - Attack of the Clones"
            )
        )

        val expected = listOf(
            MovieData(
                id = 1,
                posterPath = "somePath1",
                releaseDate = "2002-01-01",
                title = "Star Wars: Episode II - Attack of the Clones"
            )
        )
        val actual = mapper.map(movies = movies)
        assertEquals(expected, actual)
        movieMapper.checkMapCalledCount(1)
        order.check(listOf(TO_MOVIE_MAP))
    }

    @Test
    fun test_map_two_items() {
        val movies = listOf(
            MovieCloud(
                id = 0,
                posterPath = "somePath0",
                releaseDate = "1999-01-01",
                title = "Star Wars: Episode I - The Phantom Menace"
            ),
            MovieCloud(
                id = 1,
                posterPath = "somePath1",
                releaseDate = "2002-01-01",
                title = "Star Wars: Episode II - Attack of the Clones"
            )
        )

        val expected = listOf(
            MovieData(
                id = 0,
                posterPath = "somePath0",
                releaseDate = "1999-01-01",
                title = "Star Wars: Episode I - The Phantom Menace"
            ),
            MovieData(
                id = 1,
                posterPath = "somePath1",
                releaseDate = "2002-01-01",
                title = "Star Wars: Episode II - Attack of the Clones"
            )
        )
        val actual = mapper.map(movies = movies)
        assertEquals(expected, actual)
        movieMapper.checkMapCalledCount(2)
        order.check(listOf(TO_MOVIE_MAP, TO_MOVIE_MAP))
    }
}