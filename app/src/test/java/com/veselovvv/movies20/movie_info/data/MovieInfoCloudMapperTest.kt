package com.veselovvv.movies20.movie_info.data

import com.veselovvv.movies20.core.Order
import com.veselovvv.movies20.movie_info.data.FakeToMovieInfoMapper.Companion.TO_MOVIE_INFO_MAP
import com.veselovvv.movies20.movie_info.data.cloud.MovieInfoCloud
import com.veselovvv.movies20.movie_info.data.cloud.MovieInfoCloudMapper
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MovieInfoCloudMapperTest {
    private lateinit var order: Order
    private lateinit var movieInfoMapper: FakeToMovieInfoMapper
    private lateinit var mapper: MovieInfoCloudMapper

    @Before
    fun setup() {
        order = Order()
        movieInfoMapper = FakeToMovieInfoMapper.Base(order)
        mapper = MovieInfoCloudMapper.Base(movieInfoMapper = movieInfoMapper)
    }

    @Test
    fun test_mapping() {
        val expected = MovieInfoData(
            budget = 100000,
            overview = "Some overview here",
            posterPath = "somePath",
            releaseDate = "2002-01-01",
            revenue = 10000L,
            runtime = 90,
            title = "Star Wars: Episode II - Attack of the Clones",
            rating = 4.9
        )
        val actual = mapper.map(
            movieInfo = MovieInfoCloud(
                budget = 100000,
                overview = "Some overview here",
                posterPath = "somePath",
                releaseDate = "2002-01-01",
                revenue = 10000L,
                runtime = 90,
                title = "Star Wars: Episode II - Attack of the Clones",
                rating = 4.9
            )
        )
        assertEquals(expected, actual)
        movieInfoMapper.checkMapCalledCount(1)
        order.check(listOf(TO_MOVIE_INFO_MAP))
    }
}