package com.example.mazadytask.di

import com.apollographql.apollo.ApolloClient
import com.example.mazadytask.data.repository.LaunchesRepositoryImpl
import com.example.mazadytask.domain.repository.LaunchesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideLaunchesRepository(
        apolloClient: ApolloClient
    ): LaunchesRepository = LaunchesRepositoryImpl(appApolloClient = apolloClient)
}