package com.example.mazadytask.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.apollographql.apollo.ApolloClient
import com.example.mazadytask.data.mappers.toAppError
import com.example.mazadytask.data.mappers.toDomainLaunchDetails
import com.example.mazadytask.data.paging.LaunchesPagingSource
import com.example.mazadytask.domain.model.AppError
import com.example.mazadytask.domain.model.AppResult
import com.example.mazadytask.domain.model.LaunchDetails
import com.example.mazadytask.domain.model.LaunchListItem
import com.example.mazadytask.domain.repository.LaunchesRepository
import com.example.mazadytask.graphql.LaunchDetailsQuery
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LaunchesRepositoryImpl @Inject constructor(
    private val appApolloClient: ApolloClient
) : LaunchesRepository {

    override fun launchesPaging(pageSize: Int): Flow<PagingData<LaunchListItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                enablePlaceholders = false,
                prefetchDistance = 5
            ),
            pagingSourceFactory = {
                LaunchesPagingSource(
                    apollo = appApolloClient,
                    pageSize = pageSize
                )
            }
        ).flow
    }

    override suspend fun getLaunchDetails(launchId: String): AppResult<LaunchDetails> {
        return try {
            val response = appApolloClient.query(
                LaunchDetailsQuery(launchId = launchId)
            ).execute()

            val data = response.data?.launch

            when {
                response.hasErrors() -> AppResult.Error(
                    AppError.Server(code = null, message = response.errors?.firstOrNull()?.message)
                )

                data == null -> AppResult.Error(
                    AppError.Server(code = null, message = "Launch not found")
                )

                else -> AppResult.Success(data = data.toDomainLaunchDetails())
            }
        } catch (e: Exception) {
            AppResult.Error(e.toAppError())
        }
    }
}