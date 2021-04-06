package com.example.demo.domain.company.dto

import com.example.demo.domain.company.CompanyStatus
import java.time.Instant

data class CompanyReqDto(
    var name: String,
    var address: String? = null,
    var phone: String? = null,
    var email: String,
)

data class CompanySearchContext(
    var searchType: SearchType,
    var searchKeyword: String?,
    var order: Long,
    var companyStatus: CompanyStatus,
    var startDateTime: Instant,
    var endDateTime: Instant
) {
    fun getSearchKeywordWithLikeCondition() = "%$searchKeyword%"
}

enum class SearchType(val value: String) {
    NAME("name"),
    ADDRESS("address"),
    PHONE("phone");
}
