package com.example.demo.user.setup

import com.example.demo.domain.user.dto.UserSignUpReqDto
import com.example.demo.domain.user.dto.UserUpdateDto

class UserDtoBuilder {
    companion object {
        fun getUserSignUpReqDto() = UserSignUpReqDto(email = "test@gmail.com", name = "test", password = "1234")
        fun getUserUpdateDto() = UserUpdateDto("testName")
    }
}