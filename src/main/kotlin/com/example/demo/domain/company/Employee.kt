package com.example.demo.domain.company

import javax.persistence.*

@Entity
@Table(name = "employee")
class Employee() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_no")
    val id: Long = 0

    @Column(name = "name")
    lateinit var name: String
        private set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_no")
    lateinit var company: Company
        private set

    constructor(name: String) : this() {
        this.name = name
    }

    fun updateCompany(company: Company) {
        this.company = company
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Employee

        if (id != other.id) return false
        if (name != other.name) return false
        if (company != other.company) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + company.hashCode()
        return result
    }

    override fun toString(): String {
        return "Employee(id=$id, name='$name', company=$company)"
    }
}