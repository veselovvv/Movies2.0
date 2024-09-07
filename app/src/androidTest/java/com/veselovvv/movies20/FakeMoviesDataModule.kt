package com.veselovvv.movies20

import com.veselovvv.movies20.movies.data.MoviesRepository
import com.veselovvv.movies20.movies.di.MoviesDataModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [MoviesDataModule::class] // replaces MoviesDataModule with this fake one
)
class FakeMoviesDataModule {
    @Provides
    @Singleton
    fun provideMoviesRepository(): MoviesRepository = FakeMoviesRepository()
}