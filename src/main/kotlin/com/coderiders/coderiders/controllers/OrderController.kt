package com.coderiders.coderiders.controllers

import com.coderiders.coderiders.model.Offer
import com.coderiders.coderiders.model.Order
import com.coderiders.coderiders.model.User
import com.coderiders.coderiders.repository.OfferRepository
import com.coderiders.coderiders.repository.OrderRepository
import com.coderiders.coderiders.repository.UserRepository
import jakarta.persistence.*
import org.springframework.web.bind.annotation.*
import java.time.Duration
import java.time.Instant
import java.util.*
import kotlin.jvm.optionals.getOrNull

@RestController
class OrderController(
    private val userRepository: UserRepository,
    private val orderRepository: OrderRepository,
    private val offerRepository: OfferRepository,
) {
    @GetMapping("/order")
    fun getOrder(): List<Order> {
        return orderRepository.findAll()
    }
    @GetMapping("/order/{orderId}")
    fun getSingleOrder(@PathVariable orderId:Long): Optional<Order> {
        return orderRepository.findById(orderId)
    }
    @GetMapping("/user/{userId}/order")
    fun getOrderByUser(@PathVariable userId: Long): List<Order> {

        userRepository.findById(userId).getOrNull()?.let {
            return orderRepository.findAllByUser(it)
        }
        return listOf()
    }


    data class OrderRequest(
        val startDate:Instant,
        val endDate:Instant,
        val offerId:Long,
        )

    @PostMapping("/user/{userId}/order")
    fun insertOrder(@PathVariable userId: Long, @RequestBody order: OrderRequest): Order?{
        val user = userRepository.findById(userId)
        val offer = offerRepository.findById(order.offerId)
        offer.getOrNull()?.let {
            offer ->
            user.getOrNull()?.let {
                return orderRepository.save(
                    Order(
                        startDate = order.startDate,
                        endDate = order.endDate,
                        user = it,
                        offer = offer
                    )
                )
            }
        }
        throw EntityNotFoundException()
    }

    @DeleteMapping("/user/{userId}/order/{orderId}")
    fun deleteOrder(@PathVariable userId: Long,@PathVariable orderId: Long) {
        orderRepository.deleteById(orderId)
    }

    data class UpdateOrderRequest(
        val startDate:Optional<Instant>,
        val endDate:Optional<Instant>,
    )

    @PatchMapping("/user/{userId}/order/{orderId}")
    fun updateOrder(@PathVariable userId: Long,@PathVariable orderId: Long, @RequestBody updateOrderRequest: UpdateOrderRequest): Order{
        val user = userRepository.findById(userId)
        user.getOrNull()?.let {
            val order = orderRepository.findById(orderId)
            order.getOrNull()?.let { order ->
                val newOrder = Order(
                    id = order.id,
                    user = it,
                    startDate = updateOrderRequest.startDate.orElse(order.startDate),
                    endDate = updateOrderRequest.endDate.orElse(order.endDate),
                    offer = order.offer
                )
                orderRepository.save(newOrder)
            }
        }
        throw EntityNotFoundException()
    }
}
