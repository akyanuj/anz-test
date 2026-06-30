package com.test.anzapplication.feature_users.domain

import com.test.anzapplication.feature_users.common.DataResult
import com.test.anzapplication.feature_users.common.DomainError
import com.test.anzapplication.feature_users.domain.usecase.GetUsersUseCase
import com.test.anzapplication.feature_users.fakes.FakeUserRepository
import com.test.anzapplication.feature_users.fakes.users
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class GetUsersUseCaseTest {

    private lateinit var repository: FakeUserRepository
    private lateinit var useCase: GetUsersUseCase

    @Before
    fun setup() {
        repository = FakeUserRepository()
        useCase = GetUsersUseCase(repository)

    }

    @Test
    fun `should return users when repository returns success`() = runTest {
        repository.result = DataResult.Success(users)
        val result = useCase()
        assertTrue(result is DataResult.Success)
        assertEquals(users, (result as DataResult.Success).data)
    }

    @Test
    fun `should return error when repository returns failure`() = runTest {

        val error = DomainError.NoInternet
        repository.result = DataResult.Failure(error)

        val result = useCase()

        assertTrue(result is DataResult.Failure)
        assertEquals(error, (result as DataResult.Failure).error)
    }
}