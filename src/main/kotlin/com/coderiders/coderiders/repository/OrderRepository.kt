package com.coderiders.coderiders.repository

import com.coderiders.coderiders.model.Order
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository :JpaRepository<Order,Long>{
}