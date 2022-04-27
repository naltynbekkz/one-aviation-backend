package com.naltynbekkz.oneaviation.flight

import com.naltynbekkz.oneaviation.util.entity.Info
import com.naltynbekkz.oneaviation.util.entity.Location

data class FlightSearchParams(
    val passengerCount: Int,
    val departure: Info,
    val arrival: Location,
)