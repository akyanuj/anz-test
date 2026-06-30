package com.test.anzapplication.feature_users.presentation.viewmodel

import app.cash.turbine.test
import com.test.anzapplication.feature_users.common.DataResult
import com.test.anzapplication.feature_users.common.DomainError
import com.test.anzapplication.feature_users.domain.usecase.GetUsersUseCase
import com.test.anzapplication.feature_users.fakes.FakeUserRepository
import com.test.anzapplication.feature_users.fakes.users
import com.test.anzapplication.feature_users.presentation.state.UsersIntent
import com.test.anzapplication.feature_users.presentation.state.UsersUiState
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

//Load -loading, success, failure
//Refresh -refreshing,success,failure

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class UsersViewModelTest {
    private lateinit var repository: FakeUserRepository
    private lateinit var useCase: GetUsersUseCase
    private lateinit var viewModel: UsersViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        repository = FakeUserRepository()
        useCase = GetUsersUseCase(repository)
        viewModel = UsersViewModel(useCase)


    }

    @Test
    fun `initial state- empty`() {
        val state = viewModel.uiState.value
        assertTrue(state.users.isEmpty())
        assertFalse(state.isLoading)
        assertFalse(state.isRefreshing)
        assertNull(state.error)
        assertFalse(state.hasError)
    }

    @Test
    fun `load loading state`() = runTest {
        repository.result = DataResult.Success(users)

        viewModel.uiState.test {

            assertEquals(UsersUiState(), awaitItem())

            viewModel.onIntent(UsersIntent.Load)

            val loading = awaitItem()
            assertTrue(loading.isLoading)
            assertFalse(loading.isRefreshing)
            assertTrue(loading.users.isEmpty())
            assertNull(loading.error)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `load - emit success state`() = runTest {

        repository.result = DataResult.Success(users)

        viewModel.onIntent(UsersIntent.Load)

        advanceUntilIdle()
        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertFalse(state.isRefreshing)
        assertEquals(users, state.users)
        assertNull(state.error)
    }

    @Test
    fun `load - emit  fails`() = runTest {
        val error = DomainError.NoInternet
        repository.result = DataResult.Failure(error)

        viewModel.onIntent(UsersIntent.Load)

        advanceUntilIdle()

        val state = viewModel.uiState.value

        assertTrue(state.users.isEmpty())
        assertFalse(state.isLoading)
        assertFalse(state.isRefreshing)
        assertTrue(state.hasError)
        assertEquals(error, state.error)
    }

    @Test
    fun `refresh  emit refreshing state`() = runTest {
        repository.result = DataResult.Success(users)

        viewModel.uiState.test {
            assertEquals(UsersUiState(), awaitItem())

            viewModel.onIntent(UsersIntent.Refresh)

            val refreshing = awaitItem()
            assertFalse(refreshing.isLoading)
            assertTrue(refreshing.isRefreshing)
            assertTrue(refreshing.users.isEmpty())
            assertNull(refreshing.error)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `refresh  emit success state`() = runTest {

        repository.result = DataResult.Success(users)

        viewModel.onIntent(UsersIntent.Refresh)

        advanceUntilIdle()

        val state = viewModel.uiState.value

        assertFalse(state.isLoading)
        assertFalse(state.isRefreshing)
        assertEquals(users, state.users)
        assertNull(state.error)
    }

    @Test
    fun `refresh  update error when  fails`() = runTest {

        val error = DomainError.NoInternet
        repository.result = DataResult.Failure(error)

        viewModel.onIntent(UsersIntent.Refresh)

        advanceUntilIdle()

        val state = viewModel.uiState.value

        assertTrue(state.users.isEmpty())
        assertFalse(state.isLoading)
        assertFalse(state.isRefreshing)
        assertTrue(state.hasError)
        assertEquals(error, state.error)
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}