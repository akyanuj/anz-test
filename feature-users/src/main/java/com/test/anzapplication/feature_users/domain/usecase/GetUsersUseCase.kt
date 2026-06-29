package com.test.anzapplication.feature_users.domain.usecase

import com.test.anzapplication.feature_users.data.common.DataResult
import com.test.anzapplication.feature_users.domain.model.User
import com.test.anzapplication.feature_users.domain.repository.UserRepository
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(): DataResult<List<User>> {
        return repository.getUsers()
    }
}