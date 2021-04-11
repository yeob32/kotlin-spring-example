package com.example.demo.domain.company

import com.example.demo.domain.company.dto.CompanyResDto
import com.example.demo.domain.company.dto.CompanySearchContext
import com.example.demo.domain.company.extension.extractEmployeeNames
import com.example.demo.domain.company.extension.extractEmployeeNamesBySearch
import com.example.demo.domain.company.repository.CompanyRepository
import com.example.demo.domain.company.repository.specification.CompanySpecs
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
    fun getCompanyBySearchContextWithPageable(
        companySearchContext: CompanySearchContext,
        pageable: Pageable
    ): Page<Company> =
        companyRepository.findAll(CompanySpecs.searchWith(companySearchContext), pageable)

    @Transactional(readOnly = true)
    fun getAllEmployeeNamesBySearchContextWithPageable(
        companySearchContext: CompanySearchContext,
        pageable: Pageable
    ): List<String> =
        companyRepository.findAll(CompanySpecs.searchWith(companySearchContext), pageable)
            .extractEmployeeNamesBySearch()

    @Transactional(readOnly = true)
    fun getAllEmployeeNamesWithQueryDSL(
        companySearchContext: CompanySearchContext,
        pageable: Pageable
    ): List<String> =
        companyRepository.search(companySearchContext, pageable).extractEmployeeNamesBySearch()

    @Transactional(readOnly = true)
    fun getCompanyWithJPAQueryFactory(
        companySearchContext: CompanySearchContext,
        pageable: Pageable
    ): Page<CompanyResDto> = companyRepository.searchWithJPAQueryFactory(companySearchContext, pageable)
}