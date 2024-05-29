package com.veselovvv.movies20.movie_info.di

import com.google.gson.Gson
import com.veselovvv.movies20.movie_info.data.MovieInfoRepository
import com.veselovvv.movies20.movie_info.data.ToMovieInfoMapper
import com.veselovvv.movies20.movie_info.data.cloud.MovieInfoCloudDataSource
import com.veselovvv.movies20.movie_info.data.cloud.MovieInfoCloudMapper
import com.veselovvv.movies20.movie_info.data.cloud.MovieInfoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MovieInfoDataModule {
    @Provides
    @Singleton
    fun provideMovieInfoService(retrofit: Retrofit): MovieInfoService =
        retrofit.create(MovieInfoService::class.java)

    @Provides
    @Singleton
    fun provideMovieInfoCloudDataSource(
        service: MovieInfoService,
        gson: Gson
    ): MovieInfoCloudDataSource = MovieInfoCloudDataSource.Base(service, gson)

    @Provides
    @Singleton
    fun provideToMovieInfoMapper(): ToMovieInfoMapper = ToMovieInfoMapper.Base()

    @Provides
    @Singleton
    fun provideMovieInfoCloudMapper(
        movieInfoMapper: ToMovieInfoMapper
    ): MovieInfoCloudMapper = MovieInfoCloudMapper.Base(movieInfoMapper)

    @Provides
    @Singleton
    fun provideMovieInfoRepository(
        cloudDataSource: MovieInfoCloudDataSource,
        cloudMapper: MovieInfoCloudMapper
    ): MovieInfoRepository = MovieInfoRepository.Base(cloudDataSource, cloudMapper)
}