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

    @PostMapping("/user/{userId}/car")
    fun insertCar(@PathVariable userId: Long, @RequestBody car: CarRequest): Car {
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

    @DeleteMapping("/{userId}/car/{carId}")
    fun deleteCar(@PathVariable userId: Long, @PathVariable carId: Long) {
        carRepository.deleteById(carId)
    }

    data class UpdateCarRequest(
        val model: Optional<String>,
        val buildYear: Optional<Int>
    )

    @PatchMapping("/{userId}/car/{carId}")
    fun updateCar(@PathVariable userId: Long,@PathVariable carId: Long, updateCarRequest: UpdateCarRequest): Car {
        val user = userRepository.findById(userId)
        user.getOrNull()?.let {
            val car = carRepository.findById(carId)
            car.getOrNull()?.let { ncar ->
                val newCar = Car(
                    id = ncar.id,
                    model = updateCarRequest.model.orElse(ncar.model),
                    buildYear = updateCarRequest.buildYear.orElse(ncar.buildYear),
                    user = ncar.user
                )
                carRepository.save(newCar)
            }

        }
        throw EntityNotFoundException()
    }
}