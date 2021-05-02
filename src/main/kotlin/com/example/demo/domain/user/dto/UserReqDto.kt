package com.example.demo.domain.user.dto

import com.example.demo.domain.user.User
import com.example.demo.domain.user.model.Email
import com.example.demo.domain.user.model.Password

data class UserRequestDto(
    var email: String,
    var name: String,
    var password: String
) {
}

data class UserSignUpReqDto(
    var email: String,
    var name: String,
    var password: String
) {
    fun toEntity() = User(
        email = Email(email),
        name = name,
        password = Password(password)
    )
}

data class UserUpdateDto(
    var name: String
)