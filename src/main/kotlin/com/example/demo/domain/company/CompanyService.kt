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
import java.time.Instant

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

    // 기본적으로 RuntimeException 발생 시 롤백 동작
//    @Transactional(rollbackFor = [RuntimeException::class])
//    @Transactional(rollbackFor = [Exception::class])
    fun getException() {
        try {
            companyRepository.save(
                Company(
                    name = "test_company!",
                    address = "test_adress!",
                    phone = "test_company!",
                    email = "test_email!",
                    status = CompanyStatus.OPEN,
                    order = 1,
                    createdAt = Instant.now()
                )
            )

            println("save company!!!!!!!")
            throw Exception("companyService Exception")
//            throw InvalidValueException("companyService InvalidValueException")
        } catch (e: Exception) {
            println("Error !!!!!!! : ${e.message}")
            throw e
        }
    }
}