package com.example.demo.domain.company.custom

import com.example.demo.domain.company.Company
import com.example.demo.domain.company.CompanyStatus
import com.example.demo.domain.company.QCompany
import com.example.demo.domain.company.QCompany.company
import com.example.demo.domain.company.dto.CompanySearchContext
import com.example.demo.domain.company.dto.SearchType
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.JPQLQuery
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository
import java.time.Instant

@Repository
class CompanyRepositoryImpl :
    QuerydslRepositorySupport(Company::class.java), CustomCompanyRepository {

    override fun search(search: CompanySearchContext, pageable: Pageable): Page<Company> {
        val company: QCompany = QCompany.company

        val jpaQuery: JPQLQuery<Company> = from(company)

        jpaQuery.where(
            eqStatus(search.companyStatus)?.or(
                searchKeyword(
                    search.searchType,
                    search.searchKeyword
                )?.and(betweenCreatedAt(search.startDateTime, search.endDateTime))
            ),
        )

        val companies = querydsl!!.applyPagination(pageable, jpaQuery).fetch()
        return PageImpl(companies, pageable, jpaQuery.fetchCount())
    }

    private fun searchKeyword(type: SearchType, keyword: String?): BooleanExpression? = keyword?.let {
        when (type) {
            SearchType.NAME -> company.name.eq(keyword)
            SearchType.ADDRESS -> company.address.contains(keyword)
            SearchType.PHONE -> company.phone.contains(keyword)
        }
    }

    private fun eqStatus(status: CompanyStatus?): BooleanExpression? = status?.let { company.status.eq(status) }
    private fun betweenCreatedAt(startDateTime: Instant, endDateTime: Instant): BooleanExpression =
        company.createdAt.between(startDateTime, endDateTime)
}