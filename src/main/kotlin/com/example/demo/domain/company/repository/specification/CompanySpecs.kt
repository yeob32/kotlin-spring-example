package com.example.demo.domain.company.repository.specification

import com.example.demo.domain.company.Company
import com.example.demo.domain.company.CompanyStatus
import com.example.demo.domain.company.dto.CompanySearchContext
import com.example.demo.domain.company.dto.SearchType
import javax.persistence.criteria.CriteriaBuilder

import javax.persistence.criteria.Root

import org.springframework.data.jpa.domain.Specification
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate

class CompanySpecs {
    companion object {
        fun searchWith(searchContext: CompanySearchContext): Specification<Company> {
            return Specification { root: Root<Company>, query: CriteriaQuery<*>, builder: CriteriaBuilder ->
                query.where(
                    builder.or(
                        builder.and(
                            *getPredicateWithKeyword(root, builder, searchContext).toTypedArray(),
                            getPredicateWithCreatedAtBetween(root, builder, searchContext),
                        ),
                        builder.and(getPredicateWithCompanyStatus(root, builder, searchContext))
                    )
                ).restriction
            }
        }

        private fun getPredicateWithKeyword(
            root: Root<Company>,
            builder: CriteriaBuilder,
            searchContext: CompanySearchContext
        ): ArrayList<Predicate> {
            val predicates: ArrayList<Predicate> = arrayListOf()

            searchContext.searchKeyword?.let {
                predicates.add(
                    when (searchContext.searchType) {
                        SearchType.NAME -> builder.equal(
                            root.get<Set<String>>(SearchType.NAME.value),
                            searchContext.searchKeyword
                        )
                        SearchType.ADDRESS -> builder.like(
                            root.get(SearchType.ADDRESS.value),
                            searchContext.getSearchKeywordWithLikeCondition()
                        )
                        SearchType.PHONE -> builder.like(
                            root.get(SearchType.PHONE.value),
                            searchContext.getSearchKeywordWithLikeCondition()
                        )
                    }
                )
            }

            return predicates
        }

        private fun getPredicateWithCreatedAtBetween(
            root: Root<Company>,
            builder: CriteriaBuilder,
            searchContext: CompanySearchContext
        ): Predicate {
            return builder.between(
                root.get("createdAt"),
                searchContext.startDateTime,
                searchContext.endDateTime
            )
        }

        private fun getPredicateWithCompanyStatus(
            root: Root<Company>,
            builder: CriteriaBuilder,
            searchContext: CompanySearchContext
        ): Predicate = builder.equal(root.get<Set<CompanyStatus>>("status"), searchContext.companyStatus)
    }
}