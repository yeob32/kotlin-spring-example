package com.example.demo.global.error

class NotFoundOrderException: BusinessException {
    constructor(errorCode: ErrorCode) : super(errorCode)
    constructor(message: String) : super(message, ErrorCode.NOT_FOUND_ORDER)
    constructor(message: String, errorCode: ErrorCode) : super(message, errorCode)
}