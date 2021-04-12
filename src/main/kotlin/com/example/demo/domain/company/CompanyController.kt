package com.example.demo.domain.company

import com.example.demo.domain.company.dto.CompanySearchContext
import com.example.demo.global.error.InvalidValueException
import com.example.demo.logger.Log
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class CompanyController(private val companyService: CompanyService) {

    companion object : Log

    @GetMapping(value = ["/api/company"])
    fun getCompany(
        companySearchContext: CompanySearchContext,
        @RequestParam(value = "page", defaultValue = 0.toString()) page: Int,
        @RequestParam("pageSize", defaultValue = 10.toString()) pageSize: Int
    ) = companyService.getCompanyWithJPAQueryFactory(
        companySearchContext,
        PageRequest.of(page, pageSize)
    )

    @GetMapping("/api/400")
    fun exception400() {
        logger.info("test log !!!!!!!!!!")
        companyService.getException()
    }

    @GetMapping("/api/500")
    fun exception500() {
        logger.info("test400 log !!!!!!!!!!")
        throw Exception("zzzzzzzzzzzzzzzzzzz")
    }
}