package com.naltynbekkz.oneaviation.pilot

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface PilotRepository : JpaRepository<PilotEntity, Long> {
    @Query(
        value = "select * from pilots where timestamp_deleted is null",
        nativeQuery = true
    )
    fun getNotDeleted(pageable: Pageable): Page<PilotEntity>
}