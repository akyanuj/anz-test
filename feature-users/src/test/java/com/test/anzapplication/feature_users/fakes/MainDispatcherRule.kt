package com.test.anzapplication.feature_users.fakes

import com.test.anzapplication.feature_users.domain.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {

    override fun starting(description: Description) {
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()

    }

}


val users = listOf(
    User(
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
        photo = ""
    )
)