package com.naltynbekkz.oneaviation.plane

data class CreatePlaneRequest(
    val name: String,
    val mileage: Long,
    val capacity: Int,
    val price: Float,
)