package com.coderiders.coderiders.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "table_message")
class Message(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    val text: String,
    val time: Instant,
    @JsonIgnore
    @ManyToOne
    val from: User,
    @JsonIgnore
    @ManyToOne
    val to: User,
) {
    val fromId = from.id
    val toId= to.id
}