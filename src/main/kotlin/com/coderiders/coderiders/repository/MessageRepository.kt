package com.coderiders.coderiders.repository

import com.coderiders.coderiders.model.Message
import org.springframework.data.jpa.repository.JpaRepository

interface MessageRepository: JpaRepository<Message,Long> {
}