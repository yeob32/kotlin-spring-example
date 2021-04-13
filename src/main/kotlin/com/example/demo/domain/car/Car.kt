package com.example.demo.domain.car

import org.springframework.data.repository.CrudRepository
import javax.persistence.*

@Entity
class Car(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "name")
    var name: String?,

    @Column(name = "tag")
    var tag: String? = null,
) {
    override fun toString(): String {
        return "Car(id=$id, name='$name', tag='$tag')"
    }
}

interface CarRepository : CrudRepository<Car, Long>

data class CarDto(
    val carId: Long?,
    val carName: String?,
    val carTag: String?,
)