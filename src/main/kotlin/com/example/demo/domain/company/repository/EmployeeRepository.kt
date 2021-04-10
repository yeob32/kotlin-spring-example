package com.example.demo.domain.company.repository

import com.example.demo.domain.company.Employee
import org.springframework.data.jpa.repository.JpaRepository

interface EmployeeRepository : JpaRepository<Employee, Long> {

}