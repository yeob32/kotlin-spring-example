package com.example.demo.company

import com.example.demo.domain.company.*
import org.assertj.core.api.Assertions.assertThat
import org.hibernate.engine.spi.EntityEntryFactory
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Transactional
@SpringBootTest
class CompanyServiceTest {

    @Autowired
    lateinit var companyRepository: CompanyRepository

    @Autowired
    lateinit var companyService: CompanyService

    @PersistenceContext
    lateinit var entityManager: EntityManager

    @BeforeEach
    fun init() {
        val companies = mutableListOf<Company>()

        for (i in 0..9) {
            val company = Company(
                name = "company_$i",
                address = "address_$i",
                phone = "phone_$i",
                email = "email_$i",
                order = i.toLong()
            )
            val employee = Employee(name = "employee_$i")
            company.addObject(employee)

            companies.add(company)
        }

        companyRepository.saveAll(companies)
    }

    @Test
    fun `Company여러개를_조회시_Employee가_N1_쿼리가발생한다`() {
        //given
        val employeeNames = companyService.getAllEmployeeNames()

        //then
        assertThat(employeeNames.size).isEqualTo(10)
    }

    @Test
    fun `Company_페이징_쿼리_조회`() {
        val companies = companyRepository.findAll(PageRequest.of(0, 5))
        assertThat(companies.content.size).isEqualTo(5)

        val companies2 = companyRepository.findAll(PageRequest.of(0, 10))
        assertThat(companies2.content.size).isEqualTo(1)
    }

    @Test
    fun `Company_페이징_단일_조건_쿼리_조회`() {
        val companies = companyRepository.findAllByAddressEquals("address_1", PageRequest.of(0, 10))
        assertThat(companies.content.size).isEqualTo(1)

        val companies2 = companyRepository.findAllByAddressLike("address%", PageRequest.of(0, 10))
        assertThat(companies2.content.size).isEqualTo(10)
    }

    @Test
    fun `Company_페이징_검색객체_조건_쿼리_조회`() {
        val dto = CompanyReqDto(name = "company%", address = "address_1", phone = "phone_1", email = "email_1")
        val companies =
            companyRepository.findAllBySearchContext(dto, PageRequest.of(0, 10, Sort.Direction.DESC, "order"))

        assertThat(companies.content.size).isEqualTo(1)
    }

    @Test
    fun `복잡한쿼리는_em_통해서쿼리`() {
        val query = """
            SELECT c 
            FROM Company c 
            WHERE c.name = 'company_1'
        """.trimIndent()

        val company = entityManager.createQuery(query, Company::class.java).resultList
        assertThat(company.size).isEqualTo(1)
    }
}