package com.example.demo.global.error.exception

import com.example.demo.global.error.ErrorCode

class NotFoundDataException(errorCode: ErrorCode) : BusinessException(errorCode) {
}