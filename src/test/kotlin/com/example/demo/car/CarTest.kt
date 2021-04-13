package com.example.demo.car

import com.example.demo.domain.car.Car
import com.example.demo.domain.car.CarDto
import com.example.demo.domain.car.CarMapper
import com.example.demo.domain.car.CarRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CarTest {

    @Autowired
    lateinit var carRepository: CarRepository
    @Autowired
    lateinit var carMapper: CarMapper

    @BeforeEach
    fun init() {
        val car = carRepository.save(Car(name = "test_car", tag = "test_tag"))
        println("Inserted $car")
    }

    @Test
    fun `modify_car`() {
        val car = carRepository.findById(1).get()
        val carDto = carMapper.toCarDto(car)
        println(carDto)
    }

    @Test
    fun `with_mapper`() {
        val car = carRepository.findById(1).get()
        val carDto = CarDto(1, null, null)
//        val car = carMapper.toCar(carDto)
//        println(car)
        val updatedCard = carMapper.updateWithNullAsNoChange(carDto, car)
        println(updatedCard)
    }
}