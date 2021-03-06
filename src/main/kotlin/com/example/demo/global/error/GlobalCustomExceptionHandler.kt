package com.example.demo.global.error

import com.example.demo.global.error.exception.BusinessException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice(basePackages = ["com.example.demo.domain.company"])
class GlobalCustomExceptionHandler {
    companion object {
        val log: Logger = LoggerFactory.getLogger(this::class.java)
    }

    @ExceptionHandler(BusinessException::class)
    protected fun handleBusinessException(e: BusinessException): ResponseEntity<ErrorResponse> {
        log.error("handleBusinessException : ", e)
        val errorCode = e.errorCode
        return ResponseEntity(ErrorResponse(errorCode), HttpStatus.valueOf(errorCode.status))
    }
}