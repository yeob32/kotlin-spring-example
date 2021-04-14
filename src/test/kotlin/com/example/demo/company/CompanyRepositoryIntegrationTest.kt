package com.example.demo.company

import com.example.demo.domain.company.Company
import com.example.demo.domain.company.CompanyService
import com.example.demo.domain.company.CompanyStatus
import com.example.demo.domain.company.Employee
import com.example.demo.domain.company.dto.CompanySearchContext
import com.example.demo.domain.company.dto.SearchType
import com.example.demo.domain.company.repository.CompanyRepository
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.Instant
import java.time.temporal.ChronoUnit
import javax.persistence.EntityManager

@SpringBootTest
class CompanyRepositoryIntegrationTest {

    @Autowired
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var companyRepository: CompanyRepository

    @BeforeEach
    fun init() {
        createDummy()
        entityManager.clear() // 정확한 테스트를 위해 영속성컨텍스트 비워준다.
    }

    fun createDummy() {
        val companies = mutableListOf<Company>()

        for (i in 0..9) {
            val company = Company(
                name = "company_$i",
                address = "address_$i",
                phone = "phone_$i",
                email = "email_$i",
                status = if (i % 2 == 0) CompanyStatus.OPEN else CompanyStatus.CLOSE,
                order = i.toLong(),
                createdAt = Instant.now().plus(i.toLong(), ChronoUnit.DAYS)
            )

            val employee = Employee(name = "employee_$i")
            company.addObject(employee)

            companies.add(company)
        }

        companyRepository.saveAll(companies)
    }

    internal val companySearchContext = CompanySearchContext(
        searchType = SearchType.ADDRESS,
        searchKeyword = "address",
        companyStatus = CompanyStatus.CLOSE,
        order = 1,
        startDateTime = Instant.now(),
        endDateTime = Instant.now().plus(4, ChronoUnit.DAYS)
    )
}