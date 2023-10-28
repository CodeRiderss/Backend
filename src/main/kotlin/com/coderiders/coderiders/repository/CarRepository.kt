package com.coderiders.coderiders.repository

import com.coderiders.coderiders.model.Car
import org.springframework.data.jpa.repository.JpaRepository

interface CarRepository: JpaRepository<Car, Long> {
}