package com.veselovvv.movies20.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Module
@InstallIn(ViewModelComponent::class)
class CoreDomainModule {
    // Declaration of annotation for IoDispatcher
    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    annotation class IODispatcher

    // Declaration of annotation for MainDispatcher
    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    annotation class MainDispatcher

    @IODispatcher
    @Provides
    fun provideDispatchersIO(): CoroutineDispatcher = Dispatchers.IO

    @MainDispatcher
    @Provides
    fun provideDispatchersMain(): CoroutineDispatcher = Dispatchers.Main
}