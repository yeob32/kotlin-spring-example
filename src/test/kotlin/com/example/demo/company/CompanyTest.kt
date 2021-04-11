package com.example.demo.company

import com.example.demo.domain.company.*
import com.example.demo.domain.company.dto.CompanySearchContext
import com.example.demo.domain.company.dto.SearchType
import com.example.demo.domain.company.repository.CompanyRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.Instant
import java.time.temporal.ChronoUnit

@SpringBootTest
@AutoConfigureMockMvc
class CompanyTest(
    @Autowired
    val mockMvc: MockMvc,

    @Autowired
    val companyRepository: CompanyRepository,

    @Autowired
    val companyController: CompanyController
) {

    @BeforeEach
    fun init() {
        createDummy()
    }

    @Test
    internal fun `company검색조회테스트`() {
        mockMvc.perform(
            get("/api/company")
                .param("page", "0")
                .param("pageSize", "10")
                .param("searchType", "ADDRESS")
                .param("searchKeyword", "address")
                .param("companyStatus", "CLOSE")
                .param("order", "1")
                .param("startDateTime", Instant.now().toString())
                .param("endDateTime", Instant.now().plus(4, ChronoUnit.DAYS).toString())
        )
            .andExpect(status().isOk)
            .andDo(print())
    }

    fun createDummy() {
        val companies = mutableListOf<Company>()

        for (i in 0..9) {
            val company = Company(
                name = "company_$i",
                address = "address_$i",
                phone = "phone_$i",
                email = "email_$i",
                status = if (i % 2 == 0) CompanyStatus.OPEN else CompanyStatus.CLOSE,
                order = i.toLong(),
                createdAt = Instant.now().plus(i.toLong(), ChronoUnit.DAYS)
            )

            val employee = Employee(name = "employee_$i")
            company.addObject(employee)

            companies.add(company)
        }

        companyRepository.saveAll(companies)
    }

    val companySearchContext = CompanySearchContext(
        searchType = SearchType.ADDRESS,
        searchKeyword = "address",
        companyStatus = CompanyStatus.CLOSE,
        order = 1,
        startDateTime = Instant.now(),
        endDateTime = Instant.now().plus(4, ChronoUnit.DAYS)
    )
}