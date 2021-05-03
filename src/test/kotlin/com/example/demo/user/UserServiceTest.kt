package com.example.demo.user

import com.example.demo.domain.user.User
import com.example.demo.domain.user.UserRepository
import com.example.demo.domain.user.UserService
import com.example.demo.domain.user.dto.UserSignUpReqDto
import com.example.demo.domain.user.dto.UserUpdateDto
import com.example.demo.domain.user.events.UserEvent
import com.example.demo.domain.user.model.Email
import com.example.demo.domain.user.model.Password
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.refEq
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.context.ApplicationEventPublisher
import java.util.*

@ExtendWith(value = [MockitoExtension::class])
class UserServiceTest {

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var applicationEventPublisher: ApplicationEventPublisher

    @InjectMocks
    private lateinit var userService: UserService

    private lateinit var user: User
    private lateinit var findUser: User
    private val userSignUpReqDto: UserSignUpReqDto =
        UserSignUpReqDto(email = "yeob32@gmail.com", name = "ksy", password = "1234")

    @BeforeEach
    fun setUp() {
        user = User(email = Email("yeob32@gmail.com"), name = "ksy", password = Password("1234"))
        findUser = User(id = 1, email = Email("yeob32@gmail.com"), name = "ksy", password = Password("1234"))
    }

    @Test
    fun `유저 등록 테스트`() {
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
    internal fun `유저 수정 테스트`() {
        //when
        val userUpdateDto = UserUpdateDto("testName")
        val userEvent = UserEvent(findUser)
        `when`(userRepository.findById(findUser.id!!)).thenReturn(Optional.of(findUser))

        //given
        userService.updateUser(findUser.id!!, userUpdateDto)

        //then
        verify(userRepository).findById(findUser.id!!)
        verify(applicationEventPublisher).publishEvent(refEq(userEvent))
    }

    @Test
    fun `유저 목록 조회`() {
        // given
        val users = mutableListOf(findUser)
        `when`(userRepository.findAll()).thenReturn(users)

        // when
        val findUsers = userService.getUsers()

        // then
        verify(userRepository, times(1)).findAll()
        assertThat(findUsers.size).isEqualTo(1)
    }

}