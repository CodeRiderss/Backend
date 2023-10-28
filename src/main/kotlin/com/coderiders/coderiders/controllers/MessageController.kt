package com.coderiders.coderiders.controllers

import com.coderiders.coderiders.model.Message
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
    data class GetMessage(
        val from: Long,
        val to:Long
    )
    @PostMapping("/message")
    fun getMessageFiltered(@RequestBody getMessage:GetMessage): List<Message> {
        val fromUser = userRepository.findById(getMessage.from)
        val toUser = userRepository.findById(getMessage.to)
        fromUser.getOrNull()?.let {
            from ->
            toUser.getOrNull()?.let{
               to ->
                return messageRepository.findAllMessagesBySenderOrReceiver(from, to)
            }
        }
        return listOf()

    }

    data class MessageRequest(
            val text: String,
            val time: Instant,
            val from: Long,
        val to:Long
    )

    @PostMapping("/user/{userId}/message")
    fun insertMessage(@PathVariable userId: Long, @RequestBody message: MessageRequest): Message {
        val receiver = userRepository.findById(userId)
        receiver.getOrNull()?.let { receiver ->
            val sender = userRepository.findById(message.from)
            sender.getOrNull()?.let { sender ->
                messageRepository.save(Message(
                        text = message.text,
                        time = message.time,
                        from = sender,
                        to = receiver,
                )
                )
            }
        }
        throw EntityNotFoundException()
    }


}