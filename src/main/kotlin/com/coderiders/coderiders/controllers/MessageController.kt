package com.coderiders.coderiders.controllers

import com.coderiders.coderiders.model.Message
import com.coderiders.coderiders.model.User
import com.coderiders.coderiders.repository.MessageRepository
import com.coderiders.coderiders.repository.UserRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.web.bind.annotation.*
import java.time.Instant
import kotlin.jvm.optionals.getOrNull

@RestController

class MessageController(
        val messageRepository: MessageRepository,
        private val userRepository: UserRepository

) {
    @GetMapping("/message")
    fun getMessage(): List<Message> {
        return messageRepository.findAll()

    }

    data class MessageRequest(
            val text: String,
            val time: Instant,
            val sender: Long,
    )

    @PostMapping("/user/{userId}/message")
    fun insertMessage(@PathVariable userId: Long, @RequestBody message: MessageRequest): Message {
        val receiver = userRepository.findById(userId)
        receiver.getOrNull()?.let { receiver ->
            val sender = userRepository.findById(message.sender)
            sender.getOrNull()?.let { sender ->
                messageRepository.save(Message(
                        text = message.text,
                        time = message.time,
                        sender = sender,
                        receiver = receiver,
                )
                )
            }
        }
        throw EntityNotFoundException()
    }


}