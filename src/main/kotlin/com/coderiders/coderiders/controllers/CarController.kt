package com.coderiders.coderiders.controllers

import com.coderiders.coderiders.model.Car
import com.coderiders.coderiders.model.User
import com.coderiders.coderiders.repository.CarRepository
import com.coderiders.coderiders.repository.UserRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.web.bind.annotation.*
import java.util.*
import kotlin.jvm.optionals.getOrNull

@RestController
class CarController(
    val carRepository: CarRepository,
    private val userRepository: UserRepository
) {
    @GetMapping("/car")
    fun getCars(): List<Car> {
        return carRepository.findAll()
    }

    data class CarRequest(
        val model: String,
        val buildYear: Int,
        val user: User,
    )

    @PostMapping("{userId}/car")
    fun insertCar(userId: Long, @RequestBody car: CarRequest): Car {
        val user = userRepository.findById(userId)
        user.getOrNull()?.let {
            return carRepository.save(
                Car(
                    model = car.model,
                    buildYear = car.buildYear,
                    user = it
                )
            )
        }
        throw EntityNotFoundException()
    }

    @DeleteMapping("{userId}/car/{carId}")
    fun deleteCar(userId: Long, carId: Long) {
        carRepository.deleteById(carId)
    }

    data class UpdateCarRequest(
        val model: Optional<String>,
        val buildYear: Optional<Int>
    )

    @PatchMapping("{userId}/car/{carId}")
    fun updateCar(userId: Long, carId: Long, updateCarRequest: UpdateCarRequest): Car {
        val user = userRepository.findById(userId)
        user.getOrNull()?.let {
            val car = carRepository.findById(carId)
            car.getOrNull()?.let { car ->
                val newCar = Car(
                    id = car.id,
                    model = updateCarRequest.model.orElse(car.model),
                    buildYear = updateCarRequest.buildYear.orElse(car.buildYear),
                    user = car.user
                )
            }

        }
        throw EntityNotFoundException()
    }
}