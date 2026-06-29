package com.test.anzapplication.feature_users.data

import com.test.anzapplication.feature_users.data.remote.UserDto
import com.test.anzapplication.feature_users.domain.model.User

//stateless mappings, top-level extension functions.
fun UserDto.toUser(): User {
    return User(
        id = id,
        name = name,
        company = company,
        username = username,
        email = email,
        address = address,
        zip = zip,
        state = state,
        country = country,
        phone = phone,
        photo = photo
    )
}