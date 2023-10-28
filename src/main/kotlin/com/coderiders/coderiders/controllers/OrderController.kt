package com.coderiders.coderiders.controllers

import com.coderiders.coderiders.model.Order
import com.coderiders.coderiders.repository.OrderRepository
import com.coderiders.coderiders.repository.UserRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.util.*
import kotlin.jvm.optionals.getOrNull

@RestController
class OrderController(
    private val userRepository: UserRepository,
    private val orderRepository: OrderRepository,
) {
    @GetMapping("/order")
    fun getOrder(): List<Order> {
        return orderRepository.findAll()
    }


    data class OrderRequest(
        val startDate:Instant,
        val endDate:Instant,
        )

    @PostMapping("{userId}/order")
    fun insertOrder(userId: Long, @RequestBody order: OrderRequest): Order{
        val user = userRepository.findById(userId)
        user.getOrNull()?.let {
            return orderRepository.save(
                Order(
                    startDate = order.startDate,
                    endDate = order.endDate,
                    user = it
                )
            )
        }
        throw EntityNotFoundException()
    }

    @DeleteMapping("/{userId}/order/{orderId}")
    fun deleteOrder(userId: Long, orderId: Long) {
        orderRepository.deleteById(orderId)
    }

    data class UpdateOrderRequest(
        val startDate:Optional<Instant>,
        val endDate:Optional<Instant>,
        val userId: Optional<Long>,
    )

    @PatchMapping("/{userId}/order/{orderId}")
    fun updateOrder(userId: Long, orderId: Long, updateOrderRequest: UpdateOrderRequest): Order{
        val user = userRepository.findById(userId)
        user.getOrNull()?.let {
            val order = orderRepository.findById(orderId)
            order.getOrNull()?.let { order ->
                val newOrder = Order(
                    id = order.id,
                    user = it,
                    startDate = updateOrderRequest.startDate.orElse(order.startDate),
                    endDate = updateOrderRequest.endDate.orElse(order.endDate),
                )
                orderRepository.save(newOrder)
            }
        }
        throw EntityNotFoundException()
    }
}
