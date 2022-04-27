package com.naltynbekkz.oneaviation.ticket

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TicketRepository : JpaRepository<TicketEntity, Int> {
    @Query(
        value = "select * from tickets where timestamp_deleted is null and passenger_id in (select id from passengers where user_id = :userId)",
        nativeQuery = true
    )
    fun getNotDeleted(userId: Int?, pageable: Pageable): Page<TicketEntity>


    @Query(
        value = "select * from tickets where flight_id = :flightId",
        nativeQuery = true,
    )
    fun getTicketsByFlightId(flightId: Int): List<TicketEntity>

    @Query(
        value = "select count(*) from tickets where flight_id = :flightId and timestamp_deleted is null",
        nativeQuery = true,
    )
    fun getByFlightId(flightId: Int): Int
}