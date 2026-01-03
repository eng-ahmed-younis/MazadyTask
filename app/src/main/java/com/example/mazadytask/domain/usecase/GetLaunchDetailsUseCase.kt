package com.example.mazadytask.domain.usecase

import com.example.mazadytask.domain.model.AppResult
import com.example.mazadytask.domain.model.LaunchDetails
import com.example.mazadytask.domain.repository.LaunchesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetLaunchDetailsUseCase @Inject constructor(
    private val repository: LaunchesRepository
) {
    operator fun invoke(
        launchId: String
    ): Flow<AppResult<LaunchDetails>> = flow { emit(repository.getLaunchDetails(launchId)) }
}
