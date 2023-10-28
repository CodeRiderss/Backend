package com.coderiders.coderiders.model

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name="table_message")






class Message(@Id
              @GeneratedValue(strategy = GenerationType.IDENTITY)
              var id: Long? = null,
        val text: String,
        val receiver: User,
        val time: Instant,
        val sender: User)
{

}