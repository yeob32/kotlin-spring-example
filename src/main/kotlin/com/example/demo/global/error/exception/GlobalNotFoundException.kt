package com.example.demo.global.error.exception

class GlobalNotFoundException(message: String) : GlobalBusinessException("Not found $message")