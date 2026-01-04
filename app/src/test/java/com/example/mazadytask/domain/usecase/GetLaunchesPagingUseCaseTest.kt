package com.example.mazadytask.domain.usecase

import com.example.mazadytask.domain.repository.LaunchesRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.emptyFlow
import org.junit.Before
import org.junit.Test

class GetLaunchesPagingUseCaseTest {

    private lateinit var repository: LaunchesRepository
    private lateinit var useCase: GetLaunchesPagingUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetLaunchesPagingUseCase(repository)
    }

    @Test
    fun `invoked without pageSize THEN repository is called with default 20`() {
        every { repository.launchesPaging(20) } returns emptyFlow()

        useCase()

        verify(exactly = 1) {
            repository.launchesPaging(20)
        }
    }

    @Test
    fun `invoked with custom pageSize THEN repository is called with that value`() {
        val customPageSize = 50
        every { repository.launchesPaging(customPageSize) } returns emptyFlow()

        useCase(customPageSize)

        verify(exactly = 1) {
            repository.launchesPaging(customPageSize)
        }
    }

}