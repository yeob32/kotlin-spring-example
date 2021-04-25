package com.example.demo.global.error

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
class ApiErrorResponse constructor(
    status: Int,
    code: String,
    message: String,
    var something: String?,
): ErrorResponse(status, code, message) {
}