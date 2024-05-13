package com.veselovvv.movies20.movies.di

import android.content.Context
import com.veselovvv.movies20.movies.data.MovieDataToDomainMapper
import com.veselovvv.movies20.movies.data.MoviesDataToDomainMapper
import com.veselovvv.movies20.movies.data.MoviesRepository
import com.veselovvv.movies20.movies.domain.BaseMovieDataToDomainMapper
import com.veselovvv.movies20.movies.domain.BaseMoviesDataToDomainMapper
import com.veselovvv.movies20.movies.domain.FetchMoviesUseCase
import com.veselovvv.movies20.movies.domain.MovieDomainToUiMapper
import com.veselovvv.movies20.movies.domain.MoviesDomainToUiMapper
import com.veselovvv.movies20.movies.presentation.BaseMovieDomainToUiMapper
import com.veselovvv.movies20.movies.presentation.BaseMoviesDomainToUiMapper
import com.veselovvv.movies20.movies.presentation.MovieCache
import com.veselovvv.movies20.movies.presentation.MoviesCommunication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
class MoviesDomainModule {
    @Provides
    fun provideMoviesCommunication(): MoviesCommunication = MoviesCommunication.Base()

    @Provides
    fun provideMovieDataToDomainMapper(): MovieDataToDomainMapper = BaseMovieDataToDomainMapper()

    @Provides
    fun provideMoviesDataToDomainMapper(
        movieMapper: MovieDataToDomainMapper
    ): MoviesDataToDomainMapper = BaseMoviesDataToDomainMapper(movieMapper)

    @Provides
    fun provideFetchMoviesUseCase(
        repository: MoviesRepository,
        mapper: MoviesDataToDomainMapper
    ): FetchMoviesUseCase = FetchMoviesUseCase.Base(repository, mapper)

    @Provides
    fun provideMovieDomainToUiMapper(): MovieDomainToUiMapper = BaseMovieDomainToUiMapper()

    @Provides
    fun provideMoviesDomainToUiMapper(
        movieMapper: MovieDomainToUiMapper
    ): MoviesDomainToUiMapper = BaseMoviesDomainToUiMapper(movieMapper)

    @Provides
    fun provideMovieCache(
        @ApplicationContext context: Context
    ): MovieCache.Save = MovieCache.Base(context)
}