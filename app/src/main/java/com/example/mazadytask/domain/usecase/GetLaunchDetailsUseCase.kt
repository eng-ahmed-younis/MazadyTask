package com.example.mazadytask.domain.usecase

import com.example.mazadytask.domain.model.AppResult
import com.example.mazadytask.domain.model.LaunchDetails
import com.example.mazadytask.domain.repository.LaunchesRepository
import javax.inject.Inject

class GetLaunchDetailsUseCase @Inject constructor(
    private val repository: LaunchesRepository
) {
    suspend operator fun invoke(
        launchId: String
    ): AppResult<LaunchDetails> = repository.getLaunchDetails(launchId)
}
