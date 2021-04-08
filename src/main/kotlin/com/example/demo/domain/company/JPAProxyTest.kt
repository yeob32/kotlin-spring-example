package com.example.demo.domain.company

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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Person

        if (id != other.id) return false
        if (name != other.name) return false
        if (moneys != other.moneys) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + moneys.hashCode()
        return result
    }

    override fun toString(): String {
        return "Person(id=$id, name='$name', moneys=$moneys)"
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
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Money

        if (id != other.id) return false
        if (price != other.price) return false
        if (person != other.person) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + price.hashCode()
        result = 31 * result + person.hashCode()
        return result
    }

    override fun toString(): String {
        return "Money(id=$id, price=$price, person=$person)"
    }
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