package com.test.anzapplication.feature_users.data.repository

import com.test.anzapplication.feature_users.common.DataResult
import com.test.anzapplication.feature_users.common.toDomainError
import com.test.anzapplication.feature_users.data.remote.UserApi
import com.test.anzapplication.feature_users.data.toUser
import com.test.anzapplication.feature_users.domain.model.User
import com.test.anzapplication.feature_users.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi
) : UserRepository {
    //fetching data and convert DTO -> Domain
    override suspend fun getUsers(): DataResult<List<User>> {
        return try {
            DataResult.Success(
                userApi.getUsers().map { it.toUser() }
            )
        }catch (throwable: Throwable){
            DataResult.Failure(throwable.toDomainError())
        }
    }
}