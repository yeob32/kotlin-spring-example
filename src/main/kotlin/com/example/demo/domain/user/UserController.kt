package com.example.demo.domain.user

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(private val userService: UserService) {

    @GetMapping(value = ["/api/users"])
    fun getUsers() = ResponseEntity.ok(userService.getUsers())
}