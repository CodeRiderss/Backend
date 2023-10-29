package com.coderiders.coderiders.model

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "table_offer")
class Offer(
    val startDate: Instant,
    val endDate: Instant,
    val longitude: Double,
    val latitude: Double,
    val active: Boolean,

    @ManyToOne
    val car: Car,

    @ManyToOne
    val user: User,
    val pricePerHourInCent: Long,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
){

}