package com.naltynbekkz.oneaviation.ticket

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TicketRepository : JpaRepository<TicketEntity, Long> {
    @Query(
        value = "select * from tickets where timestamp_deleted is null and passenger_id in (select id from passengers where user_id = :userId)",
        nativeQuery = true
    )
    fun getNotDeleted(userId: Long?, pageable: Pageable): Page<TicketEntity>
}