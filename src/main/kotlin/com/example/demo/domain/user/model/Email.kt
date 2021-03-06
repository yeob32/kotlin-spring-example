package com.example.demo.domain.user.model

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class Email(
        @Column(name = "email", unique = true)
        var value: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Email

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return "Email(value='$value')"
    }
}