package com.coderiders.coderiders.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "table_user")
class User(
    val name:String,
    @JsonIgnore
    val password:String,
    val description:String,
    val birthday:Instant,
    val experience:String,
    val telephone:String,
    val email:String,
    val profileUrl:String? = null,
    @OneToMany
    val ratings:List<Rating>,
    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
var id: Long? = null,
){
    val averageRating =ratings.map{ it.rating  }.average()
}