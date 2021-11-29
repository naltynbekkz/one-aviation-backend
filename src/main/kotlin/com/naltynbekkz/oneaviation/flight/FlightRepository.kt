package com.naltynbekkz.oneaviation.flight

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface FlightRepository : JpaRepository<FlightEntity, Long> {

    @Query(
        value = "select * from flights where departure_time > UNIX_TIMESTAMP(NOW())",
        nativeQuery = true
    )
    fun getFuture(): List<FlightEntity>

}