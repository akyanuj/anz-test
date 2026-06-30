package com.test.anzapplication.feature_users.data

import com.test.anzapplication.feature_users.common.DataResult
import com.test.anzapplication.feature_users.common.DomainError
import com.test.anzapplication.feature_users.data.remote.UserApi
import com.test.anzapplication.feature_users.data.remote.UserDto
import com.test.anzapplication.feature_users.data.repository.UserRepositoryImpl
import com.test.anzapplication.feature_users.fakes.FakeUserApi
import com.test.anzapplication.feature_users.fakes.users
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

@RunWith(JUnit4::class)
class UserRepositoryImplTest {

    private lateinit var api: FakeUserApi
    private lateinit var repository: UserRepositoryImpl

    @Before
    fun setup() {
        api = FakeUserApi()
        repository = UserRepositoryImpl(api)
    }

    @Test
    fun ` return success`() = runTest {
        val userDto = UserDto(
            id = 1,
            name = "John",
            company = "ANZ",
            username = "john",
            email = "john@test.com",
            address = "Street",
            zip = "560001",
            state = "KA",
            country = "India",
            phone = "9999999999",
            photo = "photo.png"
        )

        api.users = listOf(userDto)
        api.getUsers()
        val result = repository.getUsers()
        assertTrue(result is DataResult.Success)

    }

    @Test
    fun ` return no internet`() = runTest {
        api.throwable = UnknownHostException()
        val result = repository.getUsers()
        assertEquals(
            DomainError.NoInternet,
            (result as DataResult.Failure).error
        )
    }

    @Test
    fun ` return timeout error`() = runTest {

        api.throwable = SocketTimeoutException()
        val result = repository.getUsers()

        assertTrue(result is DataResult.Failure)
        assertEquals(
            DomainError.NetworkTimeout,
            (result as DataResult.Failure).error
        )
    }

    @Test
    fun `return server error`() = runTest {
        val response = Response.error<String>(
            500,
            "Server Error".toResponseBody("text/plain".toMediaType())
        )

        api.throwable = HttpException(response)
        val result = repository.getUsers()
        assertTrue(result is DataResult.Failure)
        val error = (result as DataResult.Failure).error
        assertTrue(error is DomainError.ServerError)
        assertEquals(500, (error as DomainError.ServerError).code)
    }
}