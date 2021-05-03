package com.example.demo.user

import com.example.demo.IntegrationTest
import com.example.demo.domain.user.dto.UserSignUpReqDto
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


class UserControllerTest(
    @Autowired
    val mockMvc: MockMvc,
    @Autowired
    val userGenerateService: UserGenerateService
) : IntegrationTest() {

    @Test
    internal fun `유저 생성`() {
        requestSignInUser(UserFactory.getUserSignUpReqDto())
            .andExpect(status().isOk)
            .andExpect(jsonPath("").value(1))
            .andExpect(jsonPath("id").value(1))
            .andExpect(jsonPath("email").isMap)
            .andExpect(jsonPath("userHistories").isArray)
            .andDo(print())
    }

    @Test
    internal fun `유저 조회`() {
        //given
        val user = userGenerateService.save()

        //when
        val resultActions = requestGetUserByUserId(user.id!!)

        //then
        resultActions
            .andExpect(status().isOk)
            .andExpect(jsonPath("id").value(1))
    }

    @Test
    internal fun `유저 목록 조회`() {
        //given
        userGenerateService.save(3)

        //when
        val resultActions = requestGetUsers()

        //then
        resultActions
            .andExpect(status().isOk)
            .andExpect(jsonPath("$").isArray)
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].email").exists())
            .andExpect(jsonPath("$[0].email").hasJsonPath())
    }

    private fun requestSignInUser(userSignUpReqDto: UserSignUpReqDto) = mockMvc.perform(
        post("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(ObjectMapper().writeValueAsString(userSignUpReqDto))
    ).andDo(print())

    private fun requestGetUserByUserId(id: Long) = mockMvc.perform(
        get("/api/users/{id}", id)
            .contentType(MediaType.APPLICATION_JSON)
    ).andDo(print())

    private fun requestGetUsers() = mockMvc.perform(
        get("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
    ).andDo(print())
}
