package com.coderiders.coderiders.controllers

import com.coderiders.coderiders.model.Rating
import com.coderiders.coderiders.repository.CarRepository
import com.coderiders.coderiders.repository.RatingRepository
import com.coderiders.coderiders.repository.UserRepository
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class RatingController(
        val carRepository: CarRepository,
        private val userRepository: UserRepository,
        private val ratingRepository: RatingRepository
) {
    @GetMapping("/rating")
    fun getCars(): List<Rating> {
        return ratingRepository.findAll()
    }
    @DeleteMapping("/{userId}/rating/{carId}")
    fun deleteCar(userId: Long, ratingId: Long) {
        ratingRepository.deleteById(ratingId)

    }

    data class UpdateCarRequest(
            val model: Optional<String>,
            val buildYear: Optional<Int>

    )

}