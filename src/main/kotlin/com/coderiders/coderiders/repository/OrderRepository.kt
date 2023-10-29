package com.coderiders.coderiders.repository

import com.coderiders.coderiders.model.Offer
import com.coderiders.coderiders.model.Order
import com.coderiders.coderiders.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface OrderRepository :JpaRepository<Order,Long>{
    @Query("SELECT m from Order m where m.user=:user")
    fun findAllByUser(@Param("user") user: User): List<Order>
}