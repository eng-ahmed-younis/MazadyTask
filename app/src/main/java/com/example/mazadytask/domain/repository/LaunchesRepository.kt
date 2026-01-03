package com.example.mazadytask.domain.repository

import androidx.paging.PagingData
import com.example.mazadytask.domain.model.AppResult
import com.example.mazadytask.domain.model.LaunchDetails
import com.example.mazadytask.domain.model.LaunchListItem
import kotlinx.coroutines.flow.Flow

interface LaunchesRepository {
    fun launchesPaging(pageSize: Int = 20): Flow<PagingData<LaunchListItem>>
    suspend fun getLaunchDetails(launchId: String): AppResult<LaunchDetails>
}
