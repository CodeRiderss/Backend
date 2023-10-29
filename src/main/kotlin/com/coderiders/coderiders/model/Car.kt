package com.coderiders.coderiders.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Table(name="table_car")
@Entity
class Car(
    val model:String,
    @JsonIgnore
    @ManyToOne
    val user:User,
    val buildYear:Int,
    val imageUrl:String? = null,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
) {

}