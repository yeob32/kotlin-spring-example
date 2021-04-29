package com.example.demo.domain.company.custom

import com.example.demo.domain.company.Company
import com.example.demo.domain.company.CompanyStatus
import com.example.demo.domain.company.QCompany.company
import com.example.demo.domain.company.QEmployee.employee
import com.example.demo.domain.company.dto.*
import com.querydsl.core.types.Predicate
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.JPQLQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository
import java.time.Instant
import java.time.temporal.ChronoUnit

@Repository
class CustomCompanyRepositoryImpl(private val jpaQueryFactory: JPAQueryFactory) :
    QuerydslRepositorySupport(Company::class.java), CustomCompanyRepository {

    override fun search(search: CompanySearchContext, pageable: Pageable): Page<Company> {
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

    override fun searchWithJPAQueryFactory(search: CompanySearchContext, pageable: Pageable): Page<CompanyResDto> {
        val result = jpaQueryFactory.select(QCompanyResDto(company.name, company.email))
            .from(company)
            .where(
                eqStatus(search.companyStatus)?.or(
                    searchKeyword(
                        search.searchType,
                        search.searchKeyword
                    )?.and(betweenCreatedAt(search.startDateTime, search.endDateTime))
                )
            )
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetchResults()

        return PageImpl(result.results, pageable, result.total)
    }

    override fun findAllCompanyWithEmployee(): List<CompanyWithEmployeeResDto> {
        return jpaQueryFactory.select(QCompanyWithEmployeeResDto(company.name, company.email, employee.name))
            .from(company)
            .innerJoin(company.employees, employee)
            .fetch()
    }

    override fun findAllCompany(): List<Company> {
        return jpaQueryFactory.select(company)
            .from(company)
            .leftJoin(company.employees, employee)
            .where(
                company.name.eq("asdf")
                    .andAnyOf(*changedCondition())
                    .andAnyOf(*changedCondition())
            )
            .fetch()
    }

    private fun changedCondition(): Array<Predicate> {
        val expressions = arrayListOf<Predicate>()
        expressions.add(company.email.eq("zxcv"))
        expressions.add(company.address.eq("zxcv"))
        expressions.add(
            company.createdAt.between(
                Instant.now().minus(1, ChronoUnit.DAYS),
                Instant.now().plus(1, ChronoUnit.DAYS)
            )
        )
        return expressions.toTypedArray()
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