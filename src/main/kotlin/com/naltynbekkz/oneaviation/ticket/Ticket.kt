package com.naltynbekkz.oneaviation.ticket

import com.naltynbekkz.oneaviation.flight.Flight
import com.naltynbekkz.oneaviation.passenger.Passenger
import com.naltynbekkz.oneaviation.user.User
import com.naltynbekkz.oneaviation.util.entity.Timestamp

data class Ticket(
    val id: Int,
    val flight: Flight,
    val passenger: Passenger,
    val user: User,
    val timestamp: Timestamp,
)