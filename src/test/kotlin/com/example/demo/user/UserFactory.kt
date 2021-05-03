package com.example.demo.user

import com.example.demo.domain.user.dto.UserSignUpReqDto

class UserFactory {
    companion object {
        fun getUserSignUpReqDto() = UserSignUpReqDto(email = "test@gmail.com", name = "test", password = "1234")
    }
}