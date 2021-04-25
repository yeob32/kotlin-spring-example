package com.example.demo.global.error.exception

import com.example.demo.global.error.ErrorCode
import java.lang.RuntimeException

open class BusinessException : RuntimeException {

    var errorCode: ErrorCode

    constructor(errorCode: ErrorCode): super(errorCode.message) {
        this.errorCode = errorCode
    }

    constructor(message: String, errorCode: ErrorCode): super(message) {
        this.errorCode = errorCode
    }
}