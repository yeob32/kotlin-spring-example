package com.example.demo.domain.user.exceptions

import java.lang.RuntimeException

class UserNotFoundException(message: String) : RuntimeException(message) {
}