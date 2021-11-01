package com.naltynbekkz.oneaviation.plane

import com.naltynbekkz.oneaviation.util.entity.Timestamp

class Plane(
    val id: Long,
    val name: String,
    val mileage: Long,
    val capacity: Int,
    val price: Float,
    var timestamp: Timestamp,
)