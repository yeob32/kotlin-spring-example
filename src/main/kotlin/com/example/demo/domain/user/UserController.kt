package com.example.demo.domain.user

import com.example.demo.domain.user.dto.UserSignUpReqDto
import com.example.demo.domain.user.dto.UserUpdateDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class UserController(private val userService: UserService) {

    @GetMapping(value = ["/api/users"])
    fun getUsers() = ResponseEntity.ok(userService.getUsers())

    @PostMapping(value = ["/api/users"])
    fun createUser(@RequestBody userSignUpReqDto: UserSignUpReqDto) = userService.createUser(userSignUpReqDto)

    @PutMapping(value = ["/api/users/{id}"])
    fun updateUser(@PathVariable id: Long, @RequestBody userUpdateDto: UserUpdateDto) = userService.updateUser(id, userUpdateDto)
}