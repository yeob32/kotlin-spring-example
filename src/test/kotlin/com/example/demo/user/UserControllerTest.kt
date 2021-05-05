package com.example.demo.user

import com.example.demo.IntegrationTest
import com.example.demo.domain.user.dto.UserSignUpReqDto
import com.example.demo.domain.user.dto.UserUpdateDto
import com.example.demo.user.setup.UserDtoBuilder
import com.example.demo.user.setup.UserSetupService
import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class UserControllerTest(
    @Autowired
    val mockMvc: MockMvc,
    @Autowired
    val userSetupService: UserSetupService,
    @Autowired
    val objectMapper: ObjectMapper
) : IntegrationTest() {

    @Test
    internal fun `유저 생성`() {
        requestSignInUser(UserDtoBuilder.getUserSignUpReqDto())
            .andExpect(status().isOk)
            .andExpect(jsonPath("id").isNumber)
            .andExpect(jsonPath("email").isMap)
            .andExpect(jsonPath("userHistories").isArray)
            .andDo(print())
    }

    @Test
    internal fun `유저 조회`() {
        //given
        val user = userSetupService.save()

        //when
        val resultActions = requestGetUserByUserId(user.id!!)

        //then
        resultActions
            .andExpect(status().isOk)
            .andExpect(jsonPath("id").isNumber)
    }

    @Test
    internal fun `유저 목록 조회`() {
        //given
        userSetupService.save(3)

        //when
        val resultActions = requestGetUsers()

        //then
        resultActions
            .andExpect(status().isOk)
            .andExpect(jsonPath("$").isArray)
            .andExpect(jsonPath("$[0].id").isNumber)
            .andExpect(jsonPath("$[0].email").exists())
            .andExpect(jsonPath("$[0].email").hasJsonPath())
    }

    @Test
    internal fun `유저 정보 수정`() {
        //given
        val user = userSetupService.save()
        val userUpdateDto = UserDtoBuilder.getUserUpdateDto()

        //when
        val resultActions = requestUpdateUser(user.id!!, userUpdateDto)

        //then
        resultActions.andExpect(status().isOk)
        assertThat(user.name).isEqualTo(userUpdateDto.name)
    }

    private fun requestSignInUser(userSignUpReqDto: UserSignUpReqDto) = mockMvc.perform(
        post("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userSignUpReqDto))
    ).andDo(print())

    private fun requestGetUserByUserId(id: Long) = mockMvc.perform(
        get("/api/users/{id}", id)
            .contentType(MediaType.APPLICATION_JSON)
    ).andDo(print())

    private fun requestGetUsers() = mockMvc.perform(
        get("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
    ).andDo(print())

    private fun requestUpdateUser(id: Long, userUpdateDto: UserUpdateDto) = mockMvc.perform(
        put("/api/users/{id}", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userUpdateDto))
    ).andDo(print())
}
