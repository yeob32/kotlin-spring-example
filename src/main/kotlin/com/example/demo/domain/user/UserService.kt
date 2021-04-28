package com.example.demo.domain.user

import com.example.demo.domain.user.dto.UserSignUpReqDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(private val userRepository: UserRepository) {

    @Transactional(readOnly = true)
    fun getUsers(): MutableIterable<User> = userRepository.findAll()

    @Transactional
    fun createUser(userSignUpReqDto: UserSignUpReqDto): User {
        return userRepository.save(userSignUpReqDto.toEntity())
    }

    @Transactional
    fun createUser(user: User): User {
        return userRepository.save(user)
    }
}