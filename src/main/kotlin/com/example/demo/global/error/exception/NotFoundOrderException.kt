package com.example.demo.global.error.exception

import com.example.demo.global.error.ErrorCode

class NotFoundOrderException: BusinessException {
    constructor(errorCode: ErrorCode) : super(errorCode)
    constructor(message: String) : super(message, ErrorCode.NOT_FOUND_ORDER)
    constructor(message: String, errorCode: ErrorCode) : super(message, errorCode)
}