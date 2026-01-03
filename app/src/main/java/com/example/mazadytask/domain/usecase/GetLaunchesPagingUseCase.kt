package com.example.mazadytask.domain.usecase

import androidx.paging.PagingData
import com.example.mazadytask.domain.model.LaunchListItem
import com.example.mazadytask.domain.repository.LaunchesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetLaunchesPagingUseCase @Inject constructor(
    private val repo: LaunchesRepository
) {
    operator fun invoke(pageSize: Int = 20): Flow<PagingData<LaunchListItem>> {
        return repo.launchesPaging(pageSize)
    }
}
