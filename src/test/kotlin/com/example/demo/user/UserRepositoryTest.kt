package com.example.demo.user

import com.example.demo.TestConfig
import com.example.demo.domain.user.User
import com.example.demo.domain.user.UserRepository
import com.example.demo.domain.user.model.Email
import com.example.demo.domain.user.model.Password
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import

@Import(TestConfig::class)
@DataJpaTest
class UserRepositoryTest {

    private val user: User = User(email = Email("yeob32@gmail.com"), name = "ksy", password = Password("1234"))

    @Autowired
    lateinit var userRepository: UserRepository

    @Test
    fun `create_user_test`() {
        val saveUser = userRepository.save(user)

        Assertions.assertThat(saveUser.email.value).isEqualTo(user.email.value)
        Assertions.assertThat(saveUser.name).isEqualTo(user.name)
    }
}