package com.example.demo.global.error

import org.springframework.http.HttpStatus

open class ErrorResponse constructor(
    var status: Int,
    var code: String,
    var message: String
) {
    constructor(errorCode: ErrorCode) : this(errorCode.status, errorCode.code, errorCode.message) {
        this.status = errorCode.status
        this.code = errorCode.code
        this.message = errorCode.message
    }

    val httpStatus = HttpStatus.valueOf(this.status)
}

fun main() {
    ErrorResponse(200, "asdf", "code")
    ErrorResponse(ErrorCode.INVALID_INPUT_VALUE)
}