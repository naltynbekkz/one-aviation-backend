package com.naltynbekkz.oneaviation.flight

import com.naltynbekkz.oneaviation.plane.Plane
import com.naltynbekkz.oneaviation.ticket.Ticket
import com.naltynbekkz.oneaviation.util.entity.Info
import com.naltynbekkz.oneaviation.util.entity.Location
import com.naltynbekkz.oneaviation.util.entity.Timestamp

data class Flight(
    val id: Int,
    val plane: Plane,
    val timestamp: Timestamp,
    val departure: Info,
    val arrival: Location,
    val status: FlightStatus,
    val tickets: List<Ticket>,
    val passengerCount: Int,
) {
    fun availableSeats() = plane.capacity - tickets.size
}