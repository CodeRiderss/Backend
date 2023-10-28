package com.coderiders.coderiders.controllers

import com.coderiders.coderiders.model.Rating
import com.coderiders.coderiders.repository.CarRepository
import com.coderiders.coderiders.repository.RatingRepository
import com.coderiders.coderiders.repository.UserRepository
import jakarta.persistence.*
import org.springframework.web.bind.annotation.*
import java.util.*
import kotlin.jvm.optionals.getOrNull

@RestController
class RatingController(
        val RatingRepository: RatingRepository,
        private val userRepository: UserRepository,
        private val ratingRepository: RatingRepository
) {
    @GetMapping("/rating")
    fun getRating(): List<Rating> {
        return ratingRepository.findAll()
    }

    class RatingRequest(
            val rating: Int,
            val description: String

    )

    @PostMapping("/{userId}/rating")
    fun insertRating(userId: Long, @RequestBody rating: RatingRequest): Rating {
        val user = userRepository.findById(userId)
        user.getOrNull()?.let {
            return ratingRepository.save(
                    Rating(
                            rating = rating.rating,
                            description = rating.description

                    )
            )
        }
        throw EntityNotFoundException()


    }


    @DeleteMapping("/{userId}/rating/{ratingId}")
    fun deleteRating(userId: Long, ratingId: Long) {
        ratingRepository.deleteById(ratingId)

    }

    data class UpdateRatingRequest(
            val rating: Optional<Int>,
            val description: Optional<String>,

            )

    @PatchMapping("/{userId}/rating/{ratingId}")
    fun updateRating(userId: Long, ratingId: Long, updateRatingRequest: UpdateRatingRequest): Rating {
        val user = userRepository.findById(userId)
        user.getOrNull()?.let {
            val rating = ratingRepository.findById(ratingId)
            rating.getOrNull()?.let { rating ->
                val newRating = Rating(
                        id = rating.id,
                        rating = updateRatingRequest.rating.orElse(rating.rating),
                        description = updateRatingRequest.description.orElse(rating.description),

                        )
                return ratingRepository.save(newRating)


            }

        }
        throw EntityNotFoundException()
    }
}