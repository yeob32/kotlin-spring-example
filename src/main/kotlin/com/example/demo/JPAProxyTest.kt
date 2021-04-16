package com.example.demo

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.lang.RuntimeException
import javax.persistence.*

@Entity
class Person(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(name = "name")
    var name: String,

    @JsonBackReference
    @OneToMany(mappedBy = "person", cascade = [CascadeType.PERSIST])
    var moneys: MutableList<Money> = mutableListOf()
) {
    fun addMoney(money: Money) {
        moneys.add(money)
        money.person = this
    }
}

interface PersonRepository : JpaRepository<Person, Long>

@Entity
class Money(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(name = "price")
    var price: Long = 0,

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "person_id")
    var person: Person,
) {
}

interface MoneyRepository : JpaRepository<Money, Long>

@Service
class MoneyService(
    private val moneyRepository: MoneyRepository,
    private val personRepository: PersonRepository
) {
    @Transactional(readOnly = true)
    fun getMoneys(): Iterable<MoneyPriceResDto> = moneyRepository.findAll().map { MoneyPriceResDto(it.price) }.toList()

    @Transactional(readOnly = true)
    fun getAllPersonNames(): Iterable<PersonNamesResDto> =
        moneyRepository.findAll().map { PersonNamesResDto(it.person.name) }.toList()

    @Transactional(readOnly = true)
    fun getPeople(): Iterable<PersonNamesResDto> =
        personRepository.findAll().map { PersonNamesResDto(it.name) }.toList()

    @Transactional(readOnly = true)
    fun getTotalPrice(): MoneyPriceResDto = MoneyPriceResDto(personRepository.findAll().sumOf {
        it.moneys.sumOf { money -> money.price }
    })

    @Transactional
    fun updatePersonName(): Person = personRepository.findById(1)
        .orElseThrow { throw RuntimeException("") }
        .apply { name = "aaaaaaaaaaaaaaa" }
}

data class PersonNamesResDto(
    var name: String
)

data class MoneyPriceResDto(
    var price: Long
)

@RestController
class PersonController(private val moneyService: MoneyService) {

    @GetMapping("/test1")
    fun test2() = moneyService.getMoneys()

    @GetMapping("/test2")
    fun test1() = moneyService.getAllPersonNames()

    @GetMapping("/test3")
    fun test3() = moneyService.getPeople()

    @GetMapping("/test4")
    fun test4() = moneyService.getTotalPrice()

    @GetMapping("/test5")
    fun test5() = moneyService.updatePersonName()
}

@Component
class Loader(private val personRepository: PersonRepository) {
    @Bean
    fun applicationRunner(): ApplicationRunner = ApplicationRunner {
        for (i in 0..3) {
            val person = Person(name = "test$i")
            val money = Money(price = 100, person = person)

            person.addMoney(money)
            personRepository.save(person)
        }
    }
}