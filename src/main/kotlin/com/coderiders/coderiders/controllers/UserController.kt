package com.coderiders.coderiders.controllers

import com.coderiders.coderiders.model.User
import com.coderiders.coderiders.repository.UserRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.util.*
import kotlin.jvm.optionals.getOrNull

@RestController
class UserController(
    private val userRepository: UserRepository,
) {
    @GetMapping("/user")
    fun getUser(): List<User> {
        return userRepository.findAll()
    }
    @GetMapping("/user/{userId}")
    fun getSingelUser(@PathVariable userId:Long): Optional<User> {
        return userRepository.findById(userId)
    }


    data class newUser(
        val name: String,
        val password: String,
        val description: String,
        val birthday: Instant,
        val experience: String,
        val telephone: String,
        val email: String,
    )

    @PostMapping("/register")
    fun register(@RequestBody newUser: newUser): User {
        return userRepository.save(
            User(
                name = newUser.name,
                password = newUser.password,
                description = newUser.description,
                birthday = newUser.birthday,
                experience = newUser.experience,
                telephone = newUser.telephone,
                email = newUser.email,
                ratings = listOf()
            )
        )
    }
    data class LoginUser(
        val email:String,
        val password: String
    )
    @PostMapping("/login")
    fun register(@RequestBody login: LoginUser): Long? {
        val l = userRepository.findByEmail(login.email)
        l?.let {
            if(login.password == it.password) {
                return l.id
            }
        }
        return null
    }


    @DeleteMapping("/{userId}/delete")
    fun deleteUser(userId: Long) {
        userRepository.deleteById(userId)
    }

    data class UpdateUser(
        val name: Optional<String>,
        val password: Optional<String>,
        val description: Optional<String>,
        val birthday: Optional<Instant>,
        val experience: Optional<String>,
        val telephone: Optional<String>,
        val email: Optional<String>,
    )

    @PatchMapping("/{userId}")
    fun updateUser(@PathVariable userId: Long, updateUser: UpdateUser): User {
        val user = userRepository.findById(userId)
        user.getOrNull()?.let { user ->
            userRepository.save(
                User(
                    name = updateUser.name.orElse(user.name),
                    password = updateUser.password.orElse(user.password),
                    description = updateUser.description.orElse(user.description),
                    birthday = updateUser.birthday.orElse(user.birthday),
                    experience = updateUser.experience.orElse(user.experience),
                    telephone = updateUser.experience.orElse(user.telephone),
                    email = updateUser.email.orElse(user.email),
                    ratings = user.ratings,
                    id = user.id
                )
            )

        }
        throw EntityNotFoundException()
    }
}
