package com.naltynbekkz.oneaviation.flight

import com.naltynbekkz.oneaviation.plane.Plane

data class LogsResponse(
    val plane: Plane,
    val flights: List<Flight>
)