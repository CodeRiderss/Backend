package com.coderiders.coderiders.repository

import com.coderiders.coderiders.model.Offer
import org.springframework.data.jpa.repository.JpaRepository

interface OfferRepository:JpaRepository<Offer, Long> {
}