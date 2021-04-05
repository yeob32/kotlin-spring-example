package com.example.demo.domain.company

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class CompanyService(
        private val companyRepository: CompanyRepository,
) {
    fun getAllEmployeeNames(): List<String> {
        return extractEmployeeNames(companyRepository.findAll())
    }

    /**
     * Lazy Load 를 수행하기 위해 메소드를 별도로 생성
     */
    fun extractEmployeeNames(companies: List<Company>): List<String> {
        return companies.map { it.employees[0].name }
    }
}