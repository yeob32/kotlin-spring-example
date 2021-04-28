package com.example.demo.domain.user.model

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class Password(
        @Column(name = "password")
        var value: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Password

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return "Password(value='$value')"
    }
}