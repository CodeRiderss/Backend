package com.coderiders.coderiders.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.Instant

@Table(name = "table_order")
@Entity
class Order(
    val startDate:Instant,
    val endDate:Instant,
    @ManyToOne
    val user:User,
    @ManyToOne
    val offer: Offer,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
)