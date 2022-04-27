package com.naltynbekkz.oneaviation.plane

import com.naltynbekkz.oneaviation.util.entity.Timestamp

class Plane(
    val id: Int,
    val name: String,
    val mileage: Int,
    val capacity: Int,
    val price: Float,
    var timestamp: Timestamp,
)