package com.coderiders.coderiders.repository

import com.coderiders.coderiders.model.Rating
import com.coderiders.coderiders.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface RatingRepository:JpaRepository<Rating, Long> {

}