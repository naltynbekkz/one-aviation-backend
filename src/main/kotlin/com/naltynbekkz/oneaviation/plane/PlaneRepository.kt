package com.naltynbekkz.oneaviation.plane

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface PlaneRepository : JpaRepository<PlaneEntity, Long> {
    @Query(
        value = "select * from planes where timestamp_deleted is null",
        nativeQuery = true
    )
    fun getNotDeleted(): List<PlaneEntity>
}