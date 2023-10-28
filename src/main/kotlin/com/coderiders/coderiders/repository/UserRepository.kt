package com.coderiders.coderiders.repository

import com.coderiders.coderiders.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UserRepository:JpaRepository<User,Long> {
    @Query("SELECT u from User u where u.email= :email")
    fun findByEmail(@Param("email") email:String):User?
}