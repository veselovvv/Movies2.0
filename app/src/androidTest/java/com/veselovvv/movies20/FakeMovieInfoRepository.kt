package com.veselovvv.movies20

import com.veselovvv.movies20.movie_info.data.MovieInfoData
import com.veselovvv.movies20.movie_info.data.MovieInfoRepository
import com.veselovvv.movies20.movie_info.data.MoviesInfoData
import java.net.UnknownHostException

class FakeMovieInfoRepository : MovieInfoRepository {
    private var isSuccess = false

    override suspend fun fetchMovieInfo(id: Int): MoviesInfoData {
        isSuccess = !isSuccess

        return if (isSuccess) MoviesInfoData.Success(
            movieInfo = MovieInfoData(
                budget = 19000000,
                overview = "Eighties teenager Marty McFly is accidentally sent back in time to 1955...",
                posterPath = "somePath",
                releaseDate = "1985-07-03",
                revenue = 381109762L,
                runtime = 116,
                title = "Back to the Future",
                rating = 8.318
            )
        ) else MoviesInfoData.Fail(exception = UnknownHostException())
    }
}