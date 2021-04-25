package com.example.demo.global.error.exception

import java.lang.RuntimeException

open class GlobalBusinessException(message: String?) : RuntimeException(message)