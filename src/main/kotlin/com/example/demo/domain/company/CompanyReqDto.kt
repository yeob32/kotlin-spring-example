package com.example.demo.domain.company

data class CompanyReqDto(
        var name: String,
        var address: String? = null,
        var phone: String? = null,
        var email: String,
)
