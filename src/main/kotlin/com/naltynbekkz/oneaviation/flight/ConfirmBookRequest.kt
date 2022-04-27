package com.naltynbekkz.oneaviation.flight

data class ConfirmBookRequest(
    val id: Int,
    val passengers: List<Int>,
)
