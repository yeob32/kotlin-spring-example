package com.example.demo.domain.company

import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "company")
class Company() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_no")
    var id: Long = 0

    @Column(name = "name")
    lateinit var name: String
        private set

    @Column(name = "address")
    lateinit var address: String
        private set

    @Column(name = "phone")
    lateinit var phone: String
        private set

    @Column(name = "email")
    lateinit var email: String
        private set

    @Column(name = "status")
    lateinit var status: CompanyStatus
        private set

    @Column(name = "order_no")
    var order: Long = 0
        private set

    @Column(name = "created_at")
    lateinit var createdAt: Instant
        private set

    @OneToMany(mappedBy = "company", cascade = [CascadeType.ALL])
    var employees: MutableList<Employee> = mutableListOf()

    constructor(name: String) : this() {
        this.name = name
    }

    constructor(name: String, address: String, phone: String, email: String, status: CompanyStatus, order: Long, createdAt: Instant) : this() {
        this.name = name
        this.address = address
        this.phone = phone
        this.email = email
        this.status = status
        this.order = order
        this.createdAt = createdAt
    }

    fun addObject(employee: Employee) {
        this.employees.add(employee)
        employee.updateCompany(this)
    }



}