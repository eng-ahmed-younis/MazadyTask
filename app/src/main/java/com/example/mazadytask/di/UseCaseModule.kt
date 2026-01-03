package com.example.mazadytask.di

import com.example.mazadytask.domain.repository.LaunchesRepository
import com.example.mazadytask.domain.usecase.GetLaunchDetailsUseCase
import com.example.mazadytask.domain.usecase.GetLaunchesPagingUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {


    @Provides
    @Singleton
    fun provideGetLaunchesPagingUseCase(
        repository: LaunchesRepository
    ): GetLaunchesPagingUseCase = GetLaunchesPagingUseCase(repository)

    @Provides
    @Singleton
    fun provideGetLaunchDetailsUseCase(
        repository: LaunchesRepository
    ): GetLaunchDetailsUseCase = GetLaunchDetailsUseCase(repository)
}