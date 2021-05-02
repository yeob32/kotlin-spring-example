package com.example.demo.domain.user

import com.example.demo.domain.model.BaseAuditEntity
import com.example.demo.domain.user.model.Password
import javax.persistence.*

@Entity
@Table(name = "user_histories")
class UserHistory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    var user: User,

    @Column(name = "email", nullable = false)
    var email: String,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "password", nullable = false)
    var password: Password
) : BaseAuditEntity() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserHistory

        if (id != other.id) return false
        if (email != other.email) return false
        if (name != other.name) return false
        if (password != other.password) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + email.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + password.hashCode()
        return result
    }

    override fun toString(): String {
        return "UserHistory(id=$id, email=$email, name='$name', password=$password)"
    }
}