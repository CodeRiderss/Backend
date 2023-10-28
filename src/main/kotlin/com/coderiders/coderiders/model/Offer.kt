package com.coderiders.coderiders.model

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "table_offer")
class Offer(
    val startDate: Instant,
    val endDate: Instant,
    val place: String,
    val active: Boolean,
    @ManyToOne
    val user: User,
    val pricePerHourInCent: Long,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
)