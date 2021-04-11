package com.example.demo.domain.company.custom

import com.example.demo.domain.company.Company
import com.example.demo.domain.company.dto.CompanyResDto
import com.example.demo.domain.company.dto.CompanySearchContext
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CustomCompanyRepository {
    fun search(search: CompanySearchContext, pageable: Pageable): Page<Company>
    fun searchWithJPAQueryFactory(search: CompanySearchContext, pageable: Pageable): Page<CompanyResDto>
}