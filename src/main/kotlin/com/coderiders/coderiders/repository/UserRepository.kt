package com.coderiders.coderiders.repository

import com.coderiders.coderiders.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository:JpaRepository<User,Long> {
}