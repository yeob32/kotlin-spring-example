package com.example.demo.company

import com.example.demo.TestConfig
import com.example.demo.domain.company.Company
import com.example.demo.domain.company.CompanyStatus
import com.example.demo.domain.company.repository.CompanyRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.Instant

@ExtendWith(SpringExtension::class)
@Import(TestConfig::class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CompanyRepositoryTest2(
    @Autowired
    val companyRepository: CompanyRepository
) {
    @BeforeEach
    fun init() {
        companyRepository.saveAll(
            listOf(
                Company(
                    name = "test",
                    address = "test",
                    phone = "test",
                    email = "test",
                    status = CompanyStatus.OPEN,
                    order = 1L,
                    createdAt = Instant.now()
                )
            )
        )
    }

    @Test
    fun `getOne_쿼리_캐시_테스트`() {
        val company1 = companyRepository.getOne(1)
        val company2 = companyRepository.getOne(1)

        println(company1)
        println(company2)
    }

    @Nested
    inner class GetDesigns {
        @Test
        fun `조회 테스트`() {
            println("asdf")
        }

        @Test
        fun `조회 테스트222`() {
            println("asdf")
        }
    }
}