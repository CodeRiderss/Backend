package com.coderiders.coderiders.controllers

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


    data class OfferRequest(
        val startDate: Instant,
        val endDate: Instant,
        val place: String,
        val active: Boolean,
        val pricePerHourInCent: Long,
    )

    @PostMapping("{userId}/offer")
    fun insertOffer(userId: Long, @RequestBody offer: OfferRequest): Offer {
        val user = userRepository.findById(userId)
        user.getOrNull()?.let {
            return offerRepository.save(
                Offer(
                    startDate = offer.startDate,
                    endDate = offer.endDate,
                    active = offer.active,
                    pricePerHourInCent = offer.pricePerHourInCent,
                    place = offer.place,
                    user = it
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
        val place: Optional<String>,
        val active: Optional<Boolean>,
        val pricePerHourInCent: Optional<Long>,
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
                    place = updateOfferRequest.place.orElse(offer.place),
                    active = updateOfferRequest.active.orElse(offer.active),
                    pricePerHourInCent = updateOfferRequest.pricePerHourInCent.orElse(offer.pricePerHourInCent)
                )
                offerRepository.save(newOffer)
            }
        }
        throw EntityNotFoundException()
    }
}
