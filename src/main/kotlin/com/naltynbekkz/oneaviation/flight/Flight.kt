package com.naltynbekkz.oneaviation.flight

import com.naltynbekkz.oneaviation.plane.Plane
import com.naltynbekkz.oneaviation.util.entity.Info
import com.naltynbekkz.oneaviation.util.entity.Timestamp

data class Flight(
    val id: Long,
    val plane: Plane,
    val timestamp: Timestamp,
    val departure: Info,
    val arrival: Info,
) {
//    fun getArrivalTime() {
//        return (arrival.location - departure.location) / plane.speed
//    }
}