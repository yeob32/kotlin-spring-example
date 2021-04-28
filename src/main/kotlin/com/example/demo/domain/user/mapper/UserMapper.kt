package com.example.demo.domain.user.mapper

import com.example.demo.domain.user.User
import com.example.demo.domain.user.dto.UserRequestDto
import com.example.demo.domain.user.model.Email
import com.example.demo.domain.user.model.Password

class UserMapper {
    companion object {
        fun toDto(user: User): UserRequestDto = UserRequestDto(
                user.email.value,
                user.name,
                user.password.value
        )
    }

    fun toEntity(userRequestDto: UserRequestDto): User = User(
            email = Email(userRequestDto.email),
            name = userRequestDto.name,
            password = Password(userRequestDto.password)
    )
}