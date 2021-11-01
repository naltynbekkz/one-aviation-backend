package com.naltynbekkz.oneaviation.passenger

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface PassengerRepository : JpaRepository<PassengerEntity, Long> {

    @Query(
        value = "select * from passengers where timestamp_deleted is null and user_id = :userId",
        nativeQuery = true
    )
    fun getNotDeleted(userId: Long?, pageable: Pageable): Page<PassengerEntity>
}