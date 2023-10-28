package com.coderiders.coderiders.repository

import com.coderiders.coderiders.model.Rating
import org.springframework.data.jpa.repository.JpaRepository

interface RatingRepository:JpaRepository<Rating, Long> {
}