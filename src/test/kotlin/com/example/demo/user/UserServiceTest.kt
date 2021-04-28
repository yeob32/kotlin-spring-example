package com.example.demo.user

import com.example.demo.domain.user.User
import com.example.demo.domain.user.UserRepository
import com.example.demo.domain.user.UserService
import com.example.demo.domain.user.dto.UserSignUpReqDto
import com.example.demo.domain.user.model.Email
import com.example.demo.domain.user.model.Password
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(value = [MockitoExtension::class])
class UserServiceTest {

    @Mock
    private lateinit var userRepository: UserRepository

    @InjectMocks
    private lateinit var userService: UserService

    private lateinit var user: User
    private val userSignUpReqDto: UserSignUpReqDto = UserSignUpReqDto(email = "yeob32@gmail.com", name = "ksy", password = "1234")

    @BeforeEach
    fun init() {
        user = User(email = Email("yeob32@gmail.com"), name = "ksy", password = Password("1234"))
    }

    @Test
    fun `create_user_test`() {
        // given
        `when`(userRepository.save(user)).thenReturn(user)

        // when
        val saveUser = userService.createUser(userSignUpReqDto)

        // then
        verify(userRepository).save(user)
        assertThat(saveUser).isEqualTo(user)
        assertThat(saveUser.name).isEqualTo(user.name)
    }

    @Test
    fun `get_users_test`() {
        // given
        val users = mutableListOf(user)
        `when`(userRepository.findAll()).thenReturn(users)

        // when
        val users1 = userService.getUsers()

        // then
        verify(userRepository, times(1)).findAll()
    }

}