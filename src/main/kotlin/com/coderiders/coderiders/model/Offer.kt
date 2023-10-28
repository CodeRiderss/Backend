package com.coderiders.coderiders.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.Instant
@Table(name = "table_offer")
@Entity
class Offer(
   val start:Instant,
    val end:Instant,
    val place:String,
    val active:Boolean,
   @ManyToOne
    val user:User,
    val pricePerHourInCent:Long,

    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
var id: Long? = null
)