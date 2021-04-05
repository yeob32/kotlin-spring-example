package com.example.demo.domain.company

import org.springframework.web.bind.annotation.RestController

@RestController
class CompanyController(
        private val companyService: CompanyService
) {
}