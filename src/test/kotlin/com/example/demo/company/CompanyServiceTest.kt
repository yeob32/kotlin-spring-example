package com.example.demo.company

import com.example.demo.domain.company.*
import com.example.demo.domain.company.dto.CompanyReqDto
import com.example.demo.domain.company.dto.CompanySearchContext
import com.example.demo.domain.company.dto.SearchType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.time.temporal.ChronoUnit

@Transactional
@SpringBootTest
class CompanyServiceTest {

    @Autowired
    lateinit var companyRepository: CompanyRepository

    @Autowired
    lateinit var companyService: CompanyService

    @BeforeEach
    fun init() {
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

    @Test
    fun `Company여러개를_조회시_Employee가_N1_쿼리가발생한다`() {
        //given
        val employeeNames = companyService.getAllEmployeeNames()

        //then
        assertThat(employeeNames.size).isEqualTo(10)
    }

    @Test
    fun `Company_페이징_쿼리_조회`() {
        val companies = companyRepository.findAll(PageRequest.of(0, 5))
        assertThat(companies.content.size).isEqualTo(5)

        val companies2 = companyRepository.findAll(PageRequest.of(0, 10))
        assertThat(companies2.content.size).isEqualTo(1)
    }

    @Test
    fun `Company_페이징_단일_조건_쿼리_조회`() {
        val companies = companyRepository.findAllByAddressEquals("address_1", PageRequest.of(0, 10))
        assertThat(companies.content.size).isEqualTo(1)

        val companies2 = companyRepository.findAllByAddressLike("address%", PageRequest.of(0, 10))
        assertThat(companies2.content.size).isEqualTo(10)
    }

    @Test
    fun `Company_페이징_검색객체_조건_쿼리_조회`() {
        val dto = CompanyReqDto(name = "company%", address = "address_1", phone = "phone_1", email = "email_1")
        val companies =
            companyRepository.findAllBySearchContext(dto, PageRequest.of(0, 10, Sort.Direction.DESC, "order"))
        assertThat(companies.content.size).isEqualTo(1)
    }

    @Test
    fun `복잡한쿼리는_EntityManager,Criteria,QueryDSL을_이용하도록한다_하여_Criteria예제만작성해보자`() {
        val companySearchContext =
            CompanySearchContext(
                searchType = SearchType.ADDRESS,
                searchKeyword = "address",
                companyStatus = CompanyStatus.CLOSE,
                order = 1,
                startDateTime = Instant.now(),
                endDateTime = Instant.now().plus(4, ChronoUnit.DAYS)
            )

        val companies =
            companyService.getCompanyBySearchContextWithPageable(companySearchContext, PageRequest.of(0, 10))
        val companieNames =
            companyService.getAllEmployeeNamesBySearchContextWithPageable(companySearchContext, PageRequest.of(0, 10))

        println(companieNames)
        assertThat(companies.content.size).isEqualTo(7)
        assertThat(companieNames.size).isEqualTo(7)
    }
}