package com.example.demo

import com.example.demo.test.TestProfile
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

// 실제 내장 톰캣 사용 -> restTemplate 테스트
//@SpringBootTest(classes = [DemoApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SpringBootTest(classes = [DemoApplication::class])
@AutoConfigureMockMvc
@ActiveProfiles(TestProfile.LOCAL)
@Transactional
class IntegrationTest {
}