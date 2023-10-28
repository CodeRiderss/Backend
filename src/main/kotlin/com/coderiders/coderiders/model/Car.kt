package com.coderiders.coderiders.model

import jakarta.persistence.*

@Table(name="table_car")
@Entity
class Car(
    val model:String,
    @ManyToOne
    val user:User,
    val buildYear:Int,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
) {

}