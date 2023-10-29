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

    @GetMapping("/chat/user/{userId}")
    fun getMessageChat(@PathVariable userId: Long): Collection<User> {
        val fromUser = userRepository.findById(userId)
        fromUser.getOrNull()?.let { user ->
            return messageRepository.findAllMessagesByUser(user).map {
                if(it.from.id != userId) {
                    it.from
                }
                it.to
            }.filter { it.id != userId }.toSet()
        }
        return listOf()
    }

    data class GetMessage(
        val from: Long,
        val to: Long
    )

    @PostMapping("/message")
    fun getMessageFiltered(@RequestBody getMessage: GetMessage): List<Message> {
        val fromUser = userRepository.findById(getMessage.from)
        val toUser = userRepository.findById(getMessage.to)
        fromUser.getOrNull()?.let { from ->
            toUser.getOrNull()?.let { to ->
                return messageRepository.findAllMessagesBySenderAndReceiver(from, to)
            }
        }
        return listOf()

    }

    data class MessageRequest(
        val text: String,
        val to: Long
    )

    @PostMapping("/user/{userId}/message")
    fun insertMessage(@PathVariable userId: Long, @RequestBody message: MessageRequest): Message {
        val user = userRepository.findById(userId)
        user.getOrNull()?.let { user ->
            val sender = userRepository.findById(message.to)
            sender.getOrNull()?.let { to ->
                return messageRepository.save(
                    Message(
                        text = message.text,
                        time = Instant.now(),
                        from = user,
                        to = to,
                    )
                )
            }
        }
        throw EntityNotFoundException()
    }


}