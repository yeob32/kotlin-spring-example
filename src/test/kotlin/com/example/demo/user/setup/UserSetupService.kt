package com.example.demo.user.setup

import com.example.demo.domain.user.User
import com.example.demo.domain.user.UserRepository
import com.example.demo.domain.user.model.Email
import com.example.demo.domain.user.model.Password
import org.springframework.stereotype.Component

@Component
class UserSetupService(private val userRepository: UserRepository) {

    fun save() = userRepository.save(getUser())
    fun save(count: Int) {
        val users = mutableListOf<User>()
        for (i in 0..count) {
            users.add(getUser("test$i@gmail.com"))
        }
        userRepository.saveAll(users)
    }

    private fun getUser(email: String? = null) =
        User(email = Email(email ?: "test@gmail.com"), name = "test", password = Password("1234"))
}