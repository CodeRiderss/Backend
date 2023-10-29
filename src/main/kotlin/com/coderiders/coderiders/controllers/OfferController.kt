package com.coderiders.coderiders.controllers

import com.coderiders.coderiders.model.Car
import com.coderiders.coderiders.model.Offer
import com.coderiders.coderiders.repository.OfferRepository
import com.coderiders.coderiders.repository.UserRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.util.*
import kotlin.jvm.optionals.getOrNull

@RestController
class OfferController(
    private val userRepository: UserRepository,
    private val offerRepository: OfferRepository
) {
    @GetMapping("/offer")
    fun getOffer(): List<Offer> {
        return offerRepository.findAll()
    }

    @GetMapping("/user/{userId}/offer")
    fun getOfferByUser(@PathVariable userId: Long): List<Offer> {
        val user = userRepository.findById(userId)
        user?.getOrNull()?.let {
            return offerRepository.findAllByUser(it)
        }
        return listOf()
    }


    data class OfferRequest(
        val startDate: Instant,
        val endDate: Instant,
        val longitude: Double,
        val latitude: Double,
        val active: Boolean,
        val pricePerHourInCent: Long,
        val car: Car
    )

    @PostMapping("/user/{userId}/offer")
    fun insertOffer(userId: Long, @RequestBody offer: OfferRequest): Offer {
        val user = userRepository.findById(userId)
        user.getOrNull()?.let {
            return offerRepository.save(
                Offer(
                    startDate = offer.startDate,
                    endDate = offer.endDate,
                    active = offer.active,
                    pricePerHourInCent = offer.pricePerHourInCent,
                    longitude = offer.longitude,
                    latitude = offer.latitude,
                    user = it,
                    car = offer.car
                )
            )
        }
        throw EntityNotFoundException()
    }

    @DeleteMapping("/{userId}/offer/{offerId}")
    fun deleteOffer(userId: Long, offerId: Long) {
        offerRepository.deleteById(offerId)
    }

    data class UpdateOfferRequest(
        val startDate: Optional<Instant>,
        val endDate: Optional<Instant>,
        val longitude: Optional<Double>,
        val latitude: Optional<Double>,
        val active: Optional<Boolean>,
        val pricePerHourInCent: Optional<Long>,
        val car: Optional<Car>,
    )

    @PatchMapping("/{userId}/offer/{offerId}")
    fun updateOffer(userId: Long, offerId: Long, updateOfferRequest: UpdateOfferRequest): Offer {
        val user = userRepository.findById(userId)
        user.getOrNull()?.let {
            val car = offerRepository.findById(offerId)
            car.getOrNull()?.let { offer ->
                val newOffer = Offer(
                    id = offer.id,
                    user = it,
                    startDate = updateOfferRequest.startDate.orElse(offer.startDate),
                    endDate = updateOfferRequest.endDate.orElse(offer.endDate),
                    latitude = updateOfferRequest.latitude.orElse(offer.latitude),
                    longitude = updateOfferRequest.longitude.orElse(offer.longitude),
                    active = updateOfferRequest.active.orElse(offer.active),
                    pricePerHourInCent = updateOfferRequest.pricePerHourInCent.orElse(offer.pricePerHourInCent),
                    car = updateOfferRequest.car.orElse(offer.car),
                )
                offerRepository.save(newOffer)
            }
        }
        throw EntityNotFoundException()
    }
}
