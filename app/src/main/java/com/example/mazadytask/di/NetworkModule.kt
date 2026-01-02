package com.example.mazadytask.di

import android.content.Context
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.network.okHttpClient
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.mazadytask.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideChuckerInterceptor(
        @ApplicationContext context: Context
    ): Interceptor {
        return ChuckerInterceptor.Builder(context).build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        chuckerInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .apply {
                if (BuildConfig.DEBUG) addInterceptor(chuckerInterceptor)
            }
            .build()
    }


    @Provides
    @Singleton
    fun provideApolloClient(
        okHttpClient: OkHttpClient
    ): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl(BuildConfig.BASE_URL)
            .okHttpClient(okHttpClient)
            .build()
    }
}















