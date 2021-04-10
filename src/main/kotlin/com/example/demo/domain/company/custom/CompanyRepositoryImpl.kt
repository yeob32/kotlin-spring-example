package com.example.demo.domain.company.custom

import com.example.demo.domain.company.Company
import com.example.demo.domain.company.QCompany
import com.example.demo.domain.company.dto.CompanySearchContext
import com.example.demo.domain.company.dto.SearchType
import com.querydsl.jpa.JPQLQuery
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class CompanyRepositoryImpl : QuerydslRepositorySupport(Company::class.java), CustomCompanyRepository {

    override fun search(search: CompanySearchContext, pageable: Pageable): Page<Company> {
        val company: QCompany = QCompany.company

        val jpaQuery: JPQLQuery<Company> = from(company)

        search.searchKeyword?.let {
            jpaQuery.where(
                when (search.searchType) {
                    SearchType.NAME -> company.name.eq(search.searchKeyword)
                    SearchType.ADDRESS -> company.address.contains(search.searchKeyword)
                    SearchType.PHONE -> company.phone.contains(search.searchKeyword)
                }
            )
        }

        jpaQuery.where(company.createdAt.between(search.startDateTime, search.endDateTime))
        jpaQuery.where(company.status.eq(search.companyStatus))

        val companies = querydsl!!.applyPagination(pageable, jpaQuery).fetch()
        return PageImpl(companies, pageable, jpaQuery.fetchCount())
    }
}