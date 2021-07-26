package com.example.demo

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import java.time.Instant
import javax.persistence.*

@Entity
class Sample(
    @Column(name = "name")
    var name: String,
) : AbstractAuditEntity()

@MappedSuperclass
abstract class AbstractAuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Long = 0

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    lateinit var createdAt: Instant
        protected set

    @CreatedBy
    @JoinColumn(name = "create_by")
    var createdBy: String? = null
        protected set

    @LastModifiedDate
    @Column(name = "updated_at", updatable = false)
    lateinit var updatedAt: Instant
        protected set

    @LastModifiedBy
    @JoinColumn(name = "updated_by")
    var updatedBy: String? = null
        protected set
}