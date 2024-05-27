package com.veselovvv.movies20.movie_info.di

import android.content.Context
import com.veselovvv.movies20.core.ResourceProvider
import com.veselovvv.movies20.movie_info.data.MovieInfoDataToDomainMapper
import com.veselovvv.movies20.movie_info.data.MovieInfoRepository
import com.veselovvv.movies20.movie_info.data.MoviesInfoDataToDomainMapper
import com.veselovvv.movies20.movie_info.domain.BaseMovieInfoDataToDomainMapper
import com.veselovvv.movies20.movie_info.domain.BaseMoviesInfoDataToDomainMapper
import com.veselovvv.movies20.movie_info.domain.FetchMovieInfoUseCase
import com.veselovvv.movies20.movie_info.domain.MovieInfoDomainToUiMapper
import com.veselovvv.movies20.movie_info.domain.MoviesInfoDomainToUiMapper
import com.veselovvv.movies20.movie_info.presentation.BaseMovieInfoDomainToUiMapper
import com.veselovvv.movies20.movie_info.presentation.BaseMoviesInfoDomainToUiMapper
import com.veselovvv.movies20.movie_info.presentation.MoviesInfoCommunication
import com.veselovvv.movies20.movies.presentation.MovieCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
class MovieInfoDomainModule {
    @Provides
    fun provideMoviesInfoCommunication(): MoviesInfoCommunication =
        MoviesInfoCommunication.Base()

    @Provides
    fun provideMovieInfoDataToDomainMapper(): MovieInfoDataToDomainMapper =
        BaseMovieInfoDataToDomainMapper()

    @Provides
    fun provideMoviesInfoDataToDomainMapper(
        movieInfoMapper: MovieInfoDataToDomainMapper
    ): MoviesInfoDataToDomainMapper = BaseMoviesInfoDataToDomainMapper(movieInfoMapper)

    @Provides
    fun provideFetchMovieInfoUseCase(
        repository: MovieInfoRepository,
        mapper: MoviesInfoDataToDomainMapper
    ): FetchMovieInfoUseCase = FetchMovieInfoUseCase.Base(repository, mapper)

    @Provides
    fun provideMovieInfoDomainToUiMapper(): MovieInfoDomainToUiMapper =
        BaseMovieInfoDomainToUiMapper()

    @Provides
    fun provideMoviesInfoDomainToUiMapper(
        resourceProvider: ResourceProvider,
        movieInfoMapper: MovieInfoDomainToUiMapper
    ): MoviesInfoDomainToUiMapper = BaseMoviesInfoDomainToUiMapper(resourceProvider, movieInfoMapper)

    @Provides
    fun provideMovieCache(
        @ApplicationContext context: Context
    ): MovieCache.Read = MovieCache.Base(context)
}