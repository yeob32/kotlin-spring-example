package com.example.demo.company

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.data.domain.PageRequest
import org.springframework.transaction.annotation.Transactional

@Transactional
class CompanyRepositoryTest: CompanyRepositoryIntegrationTest() {

    @Test
    fun `QueryDSL_조인시_N+1발생여부_custom_dto_테스트`() {
        val companyLeftJoinEmployee = companyRepository.findAllCompanyWithEmployee()

        assertThat(companyLeftJoinEmployee.size).isEqualTo(10)
        assertThat(companyLeftJoinEmployee.first().name).isEqualTo("company_0")
        assertThat(companyLeftJoinEmployee.first().employeeName).isEqualTo("employee_0")
        assertThat(companyLeftJoinEmployee.last().name).isEqualTo("company_9")
        assertThat(companyLeftJoinEmployee.last().employeeName).isEqualTo("employee_9")
    }

    @Test
    fun `QueryDSL_조인시_N+1발생여부_테스트`() {
        val companyLeftJoinEmployee = companyRepository.findAllCompany()

        assertThat(companyLeftJoinEmployee.size).isEqualTo(10)
        assertThat(companyLeftJoinEmployee.first().name).isEqualTo("company_0")
        companyLeftJoinEmployee.forEach {
            it.employees.forEach {
                employee -> println(employee.name)
            }
        }
    }

    @Test
    fun `QueryDSL_and_or_조건절_테스트`() {
        val companies = companyRepository.searchWithJPAQueryFactory(companySearchContext, PageRequest.of(0, 10))
    }
}