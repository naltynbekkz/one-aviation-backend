package com.naltynbekkz.oneaviation.flight

import com.naltynbekkz.oneaviation.plane.Plane
import com.naltynbekkz.oneaviation.plane.PlaneEntity
import com.naltynbekkz.oneaviation.util.entity.Info
import com.naltynbekkz.oneaviation.util.entity.Location
import com.naltynbekkz.oneaviation.util.entity.Timestamp
import kotlin.random.Random

data class Flight(
    val id: Long,
    val plane: Plane,
    val timestamp: Timestamp,
    val departure: Info,
    val arrival: Location,
    val status: FlightStatus,
    val passengerCount: Int,
)