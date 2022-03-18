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

    @Query(
        value = "select * from flights where departure_time >= :from and departure_time <= :to and not (status = :status)",
        nativeQuery = true
    )
    fun getAll(from: Long, to: Long, status: FlightStatus): List<FlightEntity>

}