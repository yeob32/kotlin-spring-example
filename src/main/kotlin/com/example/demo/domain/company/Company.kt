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
        protected set

    @Column(name = "address")
    lateinit var address: String
        protected set

    @Column(name = "phone")
    lateinit var phone: String
        protected set

    @Column(name = "email")
    lateinit var email: String
        protected set

    @Column(name = "status")
    lateinit var status: CompanyStatus
        protected set

    @Column(name = "order_no")
    var order: Long = 0
        protected set

    @Column(name = "created_at")
    lateinit var createdAt: Instant
        protected set

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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Company

        if (id != other.id) return false
        if (name != other.name) return false
        if (address != other.address) return false
        if (phone != other.phone) return false
        if (email != other.email) return false
        if (status != other.status) return false
        if (order != other.order) return false
        if (createdAt != other.createdAt) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + address.hashCode()
        result = 31 * result + phone.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + status.hashCode()
        result = 31 * result + order.hashCode()
        result = 31 * result + createdAt.hashCode()
        return result
    }

    override fun toString(): String {
        return "Company(id=$id, name='$name', address='$address', phone='$phone', email='$email', status=$status, order=$order, createdAt=$createdAt"
    }


}