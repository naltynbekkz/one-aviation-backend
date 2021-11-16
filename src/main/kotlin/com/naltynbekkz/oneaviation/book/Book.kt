package com.naltynbekkz.oneaviation.book

import com.naltynbekkz.oneaviation.passenger.Passenger
import com.naltynbekkz.oneaviation.util.entity.Info
import com.naltynbekkz.oneaviation.util.entity.Location
import com.naltynbekkz.oneaviation.util.entity.Timestamp

class Book(
    val id: Long,
    val passengerCount: Int,
    val departure: Info,
    val arrival: Location,
    val passengers: List<Passenger>,
    val timestamp: Timestamp,
)