package com.naltynbekkz.oneaviation.plane

data class CreatePlaneRequest(
    val name: String,
    val mileage: Int,
    val capacity: Int,
    val price: Float,
)