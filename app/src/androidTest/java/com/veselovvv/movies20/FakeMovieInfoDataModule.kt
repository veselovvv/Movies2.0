package com.veselovvv.movies20

import com.veselovvv.movies20.movie_info.data.MovieInfoRepository
import com.veselovvv.movies20.movie_info.di.MovieInfoDataModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [MovieInfoDataModule::class] // replaces MovieInfoDataModule with this fake one
)
class FakeMovieInfoDataModule {
    @Provides
    @Singleton
    fun provideMovieInfoRepository(): MovieInfoRepository = FakeMovieInfoRepository()
}