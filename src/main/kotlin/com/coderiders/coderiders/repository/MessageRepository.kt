package com.coderiders.coderiders.repository

import com.coderiders.coderiders.model.Message
import com.coderiders.coderiders.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface MessageRepository: JpaRepository<Message,Long> {
    @Query("SELECT m from Message m where (m.to = :receiver or m.to = :sender) and (m.from = :sender or m.from = :receiver) order by m.time asc")
    fun findAllMessagesBySenderAndReceiver(@Param("receiver") receiver: User,@Param("sender") sender: User ):List<Message>
    @Query("SELECT m from Message m where (m.to = :user or m.from = :user)")
    fun findAllMessagesByUser(@Param("user") user: User) :List<Message>
}