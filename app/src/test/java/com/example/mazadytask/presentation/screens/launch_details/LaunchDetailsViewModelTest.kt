package com.example.mazadytask.presentation.screens.launch_details

import app.cash.turbine.test
import com.example.mazadytask.di.dispatcher.DispatchersProvider
import com.example.mazadytask.di.factory.LaunchDetailsParams
import com.example.mazadytask.domain.model.AppError
import com.example.mazadytask.domain.model.AppResult
import com.example.mazadytask.domain.usecase.GetLaunchDetailsUseCase
import com.example.mazadytask.presentation.base.mvi.MviEffect
import com.example.mazadytask.presentation.utils.LaunchDetailsFactory
import com.example.mazadytask.presentation.utils.UiErrorType
import com.example.mazadytask.presentation.utils.observer.ConnectivityObserver
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class LaunchDetailsViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var getLaunchDetailsUseCase: GetLaunchDetailsUseCase
    private lateinit var connectivityObserver: ConnectivityObserver
    private lateinit var dispatchers: DispatchersProvider


    private lateinit var params: LaunchDetailsParams

    private lateinit var viewModel: LaunchDetailsViewModel


    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        getLaunchDetailsUseCase = mockk()
        connectivityObserver = mockk(relaxed = true)

        dispatchers = object : DispatchersProvider {
            override val io = testDispatcher
            override val main = testDispatcher
            override val default = testDispatcher
        }

        params = LaunchDetailsParams(launchId = "123")
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    private fun createViewModel() {
        viewModel = LaunchDetailsViewModel(
            getLaunchDetailsUseCase = getLaunchDetailsUseCase,
            connectivityObserver = connectivityObserver,
            dispatchers = dispatchers,
            params = params
        )
    }


    @Test
    fun `network offline WHEN observing THEN error dialog is emitted`() = runTest {

        every { connectivityObserver.observe() } returns flowOf(
            ConnectivityObserver.State.UnAvailable
        )

        createViewModel()


        viewModel.effects.test {
            val effect = awaitItem()
            assertTrue(effect is MviEffect.OnErrorDialog)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `network becomes available WHEN first time THEN loadLaunchDetails is triggered`() =
        runTest {

            every { connectivityObserver.observe() } returns flowOf(
                ConnectivityObserver.State.Available
            )

            every { getLaunchDetailsUseCase(any()) } returns flowOf(
                AppResult.Success(LaunchDetailsFactory.create())
            )

            createViewModel()


            advanceUntilIdle()

            // Assert
            verify(exactly = 1) {
                getLaunchDetailsUseCase("123")
            }
        }


    @Test
    fun `success result WHEN loading launch details THEN state is updated`() = runTest {

        every { connectivityObserver.observe() } returns flowOf(ConnectivityObserver.State.Available)
        every { getLaunchDetailsUseCase(any()) } returns flowOf(
            AppResult.Success(
                LaunchDetailsFactory.create()
            )
        )

        createViewModel()

        advanceUntilIdle()


        val state = viewModel.viewState

        assertTrue(state.details != null)

    }

    @Test
    fun `server error WHEN loading launch details THEN server error type is set`() = runTest {

        every { connectivityObserver.observe() } returns flowOf(
            ConnectivityObserver.State.Available
        )

        every { getLaunchDetailsUseCase(any()) } returns flowOf(
            AppResult.Error(
                AppError.Server(message = "Server down")
            )
        )

        createViewModel()

        advanceUntilIdle()

        // Assert
        val state = viewModel.viewState
        assertTrue(state.errorType is UiErrorType.Server)
    }


    @Test
    fun `network emits available more times THEN loadLaunchDetails is called only once`() = runTest {

        every { connectivityObserver.observe() } returns flowOf(
            ConnectivityObserver.State.Available,
            ConnectivityObserver.State.Available
        )

        every { getLaunchDetailsUseCase(any()) } returns flowOf(
            AppResult.Success(LaunchDetailsFactory.create())
        )

        createViewModel()

        advanceUntilIdle()

        verify(exactly = 1) {
            getLaunchDetailsUseCase("123")
        }

    }


}