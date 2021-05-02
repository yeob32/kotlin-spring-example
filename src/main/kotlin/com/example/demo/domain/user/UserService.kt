package com.example.demo.domain.user

import com.example.demo.domain.user.dto.UserResDto
import com.example.demo.domain.user.dto.UserSignUpReqDto
import com.example.demo.domain.user.dto.UserUpdateDto
import com.example.demo.domain.user.events.UserEvent
import com.example.demo.domain.user.exceptions.UserNotFoundException
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionSynchronizationManager

@Service
class UserService(
    private val userRepository: UserRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) {

    @Transactional(readOnly = true)
    fun getUsers(): Iterable<UserResDto> = userRepository.findAll().toResDto()

    @Transactional(readOnly = true)
    fun getUser(id: Long): User = userRepository.findById(id)
        .orElseThrow { throw UserNotFoundException("Nof found User $id") }

    @Transactional
    fun createUser(userSignUpReqDto: UserSignUpReqDto): User {
        return userRepository.save(userSignUpReqDto.toEntity())
    }

    @Transactional
    fun createUser(user: User): User {
        return userRepository.save(user)
    }

    @Transactional
    fun updateUser(id: Long, userUpdateDto: UserUpdateDto) = getUser(id)
        .apply {
            name = userUpdateDto.name
        }.run {
            println("check updateUser !! ${TransactionSynchronizationManager.getCurrentTransactionName()}")
            applicationEventPublisher.publishEvent(UserEvent(this))
        }

    private fun Iterable<User>.toResDto() = map { it.toResDto() }
    private fun User.toResDto() = UserResDto(
        id = id!!,
        email = email,
        name = name
    )
}