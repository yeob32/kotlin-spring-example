package com.example.demo.domain.company.dto

import com.querydsl.core.annotations.QueryProjection

data class CompanyResDto @QueryProjection constructor(
    var name: String,
    var email: String
)

data class CompanyWithEmployeeResDto @QueryProjection constructor(
    var name: String,
    var email: String,
    var employeeName: String
)
