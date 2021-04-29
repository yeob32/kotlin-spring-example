package com.example.demo.domain.company

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {

    @GetMapping("/api/test")
    fun get(testDto: TestDto) {
        println("get : $testDto")
    }

    @PostMapping("/api/test")
    fun post(testDto: TestDto) {
        println("get : $testDto")
    }

    @PutMapping("/api/test")
    fun put(testDto: TestDto) {
        println("get : $testDto")
    }
}

data class TestDto(
    val number: Long?,
    val name: String?,
    val email: String?
)