package com.test.anzapplication.feature_users.data.di

import com.test.anzapplication.feature_users.data.repository.UserRepositoryImpl
import com.test.anzapplication.feature_users.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UserModule {

    @Binds
    abstract fun bindUserRepository(
        userRepository: UserRepositoryImpl
    ): UserRepository


}