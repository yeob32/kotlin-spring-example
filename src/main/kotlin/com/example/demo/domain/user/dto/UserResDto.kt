package com.example.demo.domain.user.dto

import com.example.demo.domain.user.model.Email

data class UserResDto(
    var id: Long,
    var email: Email,
    var name: String,
)
