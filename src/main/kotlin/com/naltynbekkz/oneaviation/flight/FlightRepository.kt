package com.naltynbekkz.oneaviation.flight

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface FlightRepository : JpaRepository<FlightEntity, Int> {

    @Query(
        value = "select * from flights " +
            "where departure_time > UNIX_TIMESTAMP(NOW())" +
            " and (departure_time < :departureTime + :maxTimeDifference)" +
            " and (departure_time > :departureTime - :maxTimeDifference)" +
            " and (arrival_lat - :arrivalLat) * (arrival_lat - :arrivalLat) + (arrival_long - :arrivalLong) * (arrival_long - :arrivalLong) < :maxDistanceDifference * :maxDistanceDifference" +
            " and (departure_location_lat - :departureLat) * (departure_location_lat - :departureLat) + (departure_location_long - :departureLong) * (departure_location_long - :departureLong) < :maxDistanceDifference * :maxDistanceDifference",
        nativeQuery = true
    )
    fun getSimilar(
        arrivalLat: Float,
        arrivalLong: Float,
        departureLat: Float,
        departureLong: Float,
        departureTime: Long,
        maxDistanceDifference: Float,
        maxTimeDifference: Long,
    ): List<FlightEntity>

    @Query(
        value = "select * from flights where departure_time >= :from and departure_time <= :to and exists(select * from tickets where tickets.flight_id = flights.id && tickets.timestamp_deleted is null)",
        nativeQuery = true
    )
    fun getAll(from: Long, to: Long): List<FlightEntity>

}