package com.veselovvv.movies20.movies.di

import com.google.gson.Gson
import com.veselovvv.movies20.movies.data.MoviesRepository
import com.veselovvv.movies20.movies.data.ToMovieMapper
import com.veselovvv.movies20.movies.data.cloud.MoviesCloudDataSource
import com.veselovvv.movies20.movies.data.cloud.MoviesCloudMapper
import com.veselovvv.movies20.movies.data.cloud.MoviesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MoviesDataModule {
    @Provides
    @Singleton
    fun provideMoviesService(retrofit: Retrofit): MoviesService =
        retrofit.create(MoviesService::class.java)

    @Provides
    @Singleton
    fun provideMoviesCloudDataSource(service: MoviesService, gson: Gson): MoviesCloudDataSource =
        MoviesCloudDataSource.Base(service, gson)

    @Provides
    @Singleton
    fun provideToMovieMapper(): ToMovieMapper = ToMovieMapper.Base()

    @Provides
    @Singleton
    fun provideMoviesCloudMapper(movieMapper: ToMovieMapper): MoviesCloudMapper =
        MoviesCloudMapper.Base(movieMapper)

    @Provides
    @Singleton
    fun provideMoviesRepository(
        cloudDataSource: MoviesCloudDataSource,
        cloudMapper: MoviesCloudMapper
    ): MoviesRepository = MoviesRepository.Base(cloudDataSource, cloudMapper)
}