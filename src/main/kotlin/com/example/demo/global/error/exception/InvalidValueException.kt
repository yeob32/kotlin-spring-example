package com.example.demo.global.error.exception

import com.example.demo.global.error.ErrorCode

class InvalidValueException : BusinessException {
    constructor(errorCode: ErrorCode) : super(errorCode)
    constructor(message: String) : super(message, ErrorCode.INVALID_INPUT_VALUE)
    constructor(message: String, errorCode: ErrorCode) : super(message, errorCode)
}