package com.coderiders.coderiders.controllers

import com.coderiders.coderiders.model.Car
import com.coderiders.coderiders.model.Offer
import com.coderiders.coderiders.model.User
import com.coderiders.coderiders.repository.OfferRepository
import com.coderiders.coderiders.repository.UserRepository
import jakarta.persistence.*
import org.springframework.web.bind.annotation.*
import java.time.Duration
import java.time.Instant
import java.util.*
import kotlin.collections.HashMap
import kotlin.jvm.optionals.getOrNull

@RestController
class OfferController(
    private val userRepository: UserRepository,
    private val offerRepository: OfferRepository
) {
    data class OfferWithCalculation(
        val startDate: Instant,
        val endDate: Instant,
        val longitude: Double,
        val latitude: Double,
        val active: Boolean,
        val car: Car,
        val user: User,
        val pricePerHourInCent: Long,
        var id: Long? = null,
        val priceInEuro: Double
    )
    @GetMapping("/offer")
    fun getOfferQueryDate(@RequestParam map:Map<String, String>): List<Any> {
         map["startDate"]?.let {
             startDate ->
             map["endDate"]?.let {
                 endDate ->
                 val startDate = Instant.parse(startDate)
                 val endDate = Instant.parse(endDate)

                 val offer = offerRepository.findAll().map {
                     OfferWithCalculation(
                         id = it.id,
                         startDate = it.startDate,
                         endDate = it.endDate,
                         longitude = it.longitude,
                         latitude = it.latitude,
                         active = it.active,
                         car = it.car,
                         user = it.user,
                         pricePerHourInCent = it.pricePerHourInCent,
                         priceInEuro = (Duration.between(startDate, endDate)
                             .toHours() * it.pricePerHourInCent).div(100.0)

                     )
                 }
                 return offer

            }

        }
        return offerRepository.findAll()
    }

    @GetMapping("/user/{userId}/offer")
    fun getOfferByUser(@PathVariable userId: Long): List<Offer> {
        val user = userRepository.findById(userId)
        user.getOrNull()?.let {
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
    data class OfferPriceRequest(
        val startDate:Instant,
        val endDate:Instant
    )
    @PostMapping("/offer/{offerId}")
    fun calcOfferPrice(@PathVariable offerId:Long, @RequestBody offerPriceRequest: OfferPriceRequest):Long?{
        return null
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
