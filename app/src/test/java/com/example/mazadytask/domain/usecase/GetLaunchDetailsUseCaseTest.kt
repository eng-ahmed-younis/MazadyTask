package com.example.mazadytask.domain.usecase

import com.example.mazadytask.domain.model.AppError
import com.example.mazadytask.domain.model.AppResult
import com.example.mazadytask.domain.repository.LaunchesRepository
import com.example.mazadytask.presentation.utils.LaunchDetailsFactory
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetLaunchDetailsUseCaseTest {

    private lateinit var repository: LaunchesRepository
    private lateinit var useCase: GetLaunchDetailsUseCase


    @Before
    fun setup() {
        repository = mockk()
        useCase = GetLaunchDetailsUseCase(repository)
    }

    @Test
    fun `repository returns success THEN use case emits AppResult_Success`() = runTest {

        val launchId = "123"
        val launchDetails = LaunchDetailsFactory.create()

        coEvery { repository.getLaunchDetails(launchId) } returns
                AppResult.Success(launchDetails)


        val result = useCase(launchId).first()

        assertTrue(result is AppResult.Success)
        assertEquals(launchDetails, (result as AppResult.Success).data)
    }

    @Test
    fun `repository returns error THEN use case emits AppResult_Error`() = runTest {

        val launchId = "123"
        val error = AppError.Network("No internet")

        coEvery { repository.getLaunchDetails(launchId) } returns
                AppResult.Error(error)

        val result = useCase(launchId).first()


        assertTrue(result is AppResult.Error)
        assertEquals(error, (result as AppResult.Error).error)
    }

}