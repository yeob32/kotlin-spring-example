package com.example.demo.user

import com.example.demo.domain.user.User
import com.example.demo.domain.user.UserService
import com.example.demo.domain.user.dto.UserUpdateDto
import com.example.demo.domain.user.model.Email
import com.example.demo.domain.user.model.Password
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
class UserEventTest(
    @Autowired
    val userService: UserService
) {
    @BeforeEach
    fun init() {
        userService.createUser(getUser())
    }

    @Test
    internal fun `유저 수정 후 이력 저장 이벤트 발행 검증`() {
        val updateName = "testName"
        userService.getUser(1L).run {
            userService.updateUser(id!!, UserUpdateDto(updateName))
        }
    }

    private fun getUser() = User(email = Email("yeob32@gmail.com"), name = "ksy", password = Password("1234"))
}