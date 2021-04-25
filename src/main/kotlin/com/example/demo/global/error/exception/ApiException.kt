package com.example.demo.global.error.exception

import java.lang.RuntimeException

class ApiException(
    var status: Int,
    var code: String,
    var something: String?,
    override var message: String
) : RuntimeException(message) {
}

fun main() {
    ApiException(200, "200", null, "haha")
}