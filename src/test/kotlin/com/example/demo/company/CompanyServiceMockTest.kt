package com.example.demo.company

import com.example.demo.domain.company.CompanyService
import com.example.demo.domain.company.repository.CompanyRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class CompanyServiceMockTest(

) {
    @Mock
    lateinit var companyRepository: CompanyRepository
    @InjectMocks
    lateinit var companyService: CompanyService

    @Test
    internal fun `ㅁㄴㅇㄹ`() {

        companyService.getAllEmployeeNames()
    }
}