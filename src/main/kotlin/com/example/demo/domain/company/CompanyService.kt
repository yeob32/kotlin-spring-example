package com.example.demo.domain.company

import com.example.demo.domain.company.dto.CompanySearchContext
import com.example.demo.domain.company.extension.extractEmployeeNames
import com.example.demo.domain.company.specification.CompanySpecs
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CompanyService(
    private val companyRepository: CompanyRepository,
) {
    @Transactional(readOnly = true)
    fun getAllEmployeeNames(): List<String> = companyRepository.findAll().extractEmployeeNames()

    @Transactional(readOnly = true)
    fun getCompanyBySearchContextWithPageable(companySearchContext: CompanySearchContext, pageable: Pageable): Page<Company> =
        companyRepository.findAll(CompanySpecs.searchWith(companySearchContext), pageable)
}