package com.example.mazadytask.presentation.screens.launch_list

import androidx.paging.PagingData
import app.cash.turbine.test
import com.example.mazadytask.di.dispatcher.DispatchersProvider
import com.example.mazadytask.domain.usecase.GetLaunchesPagingUseCase
import com.example.mazadytask.presentation.base.mvi.MviEffect
import com.example.mazadytask.presentation.utils.LaunchListItemFactory
import com.example.mazadytask.presentation.utils.MainDispatcherRule
import com.example.mazadytask.presentation.utils.observer.ConnectivityObserver
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class LaunchListViewModelTest {
    /** testing follow 3 steps
     * 1- arrange -> initialize objects and mocks that testing unit need to
     * 2- act -> invoke target unit and pass params that prepare in arrangement section
     *        -> hold output/ result in variable to validate later
     * 3- assert -> make sure that invoked fun match expectation
     * */

  //  private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val testDispatcher get() = mainDispatcherRule.dispatcher

    private lateinit var getLaunchesUseCase: GetLaunchesPagingUseCase
    private lateinit var connectivityObserver: ConnectivityObserver
    private lateinit var dispatchers: DispatchersProvider


    private lateinit var launchViewModel: LaunchListViewModel


    @Before
    fun setup() {
        getLaunchesUseCase = mockk()
        connectivityObserver = mockk(relaxed = true)

        dispatchers = object : DispatchersProvider {
            override val io = testDispatcher
            override val main = testDispatcher
            override val default = testDispatcher
        }

    }


    private fun createLaunchViewModel() {
        launchViewModel = LaunchListViewModel(
            getLaunchesUseCase = getLaunchesUseCase,
            connectivityObserver = connectivityObserver,
            dispatchers = dispatchers
        )
    }


    @Test
    fun `launchesPagingFlow with valid PagingData then success `() = runTest {

        // Arrange
        every { getLaunchesUseCase(any()) } returns flowOf(
            PagingData.from(
                LaunchListItemFactory.createList()
            )
        )

        createLaunchViewModel()


        // Act
        launchViewModel.launchesPagingFlow.test {
            // Waits for emission from Flow is nothing emmitted return time out
            val pagingData = awaitItem()
            assertNotNull(pagingData)

            // i using cachedIn it keep flow active as long as viewmodel active and cached paginData in vm scope
            // so flow not complete so that i cancel it manually
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `launchesPagingFlow emits empty PagingData THEN paging data is not null`() = runTest {
        // Arrange
        every { getLaunchesUseCase(any()) } returns flowOf(
            PagingData.from(emptyList())
        )

        createLaunchViewModel()

        // Act
        launchViewModel.launchesPagingFlow.test {
            val pagingData = awaitItem()
            assertNotNull(pagingData)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `launchesPagingFlow WHEN use case throws exception THEN error dialog effect is emitted`() =
        runTest {


            every { getLaunchesUseCase(any()) } returns flow {
                throw RuntimeException("Network error")
            }
            createLaunchViewModel()


            launchViewModel.effects.test {
                val job = launch {
                    launchViewModel.launchesPagingFlow.collect()
                }

                val effect = awaitItem()
                assertTrue(effect is MviEffect.OnErrorDialog)


                // THEN error dialog effect is emitted
                assertTrue(effect is MviEffect.OnErrorDialog)

                job.cancel()
                cancelAndIgnoreRemainingEvents()
            }
        }


    @Test
    fun `launchesPagingFlow WHEN not collected THEN useCase is not invoked`() = runTest {

        every { getLaunchesUseCase(any()) } returns flowOf(PagingData.from(LaunchListItemFactory.createList()))
        every { connectivityObserver.observe() } returns emptyFlow()

        createLaunchViewModel()


        verify(exactly = 0) { getLaunchesUseCase(any()) }

        val job = launch {
            launchViewModel.launchesPagingFlow.collect()
        }

        advanceUntilIdle()

        verify(exactly = 1) { getLaunchesUseCase(any()) }

        job.cancel()
    }


    @Test
    fun `launchesPagingFlow WHEN collected multiple times THEN useCase is called once`() = runTest {
        every { getLaunchesUseCase(any()) } returns flowOf(PagingData.from(LaunchListItemFactory.createList()))
        every { connectivityObserver.observe() } returns emptyFlow()
        createLaunchViewModel()

        val job1 = launch {
            launchViewModel.launchesPagingFlow.collect()
        }

        val job2 = launch {
            launchViewModel.launchesPagingFlow.collect()
        }

        advanceUntilIdle()


        verify(exactly = 1){
            getLaunchesUseCase(any())
        }

        job1.cancel()
        job2.cancel()

    }





}