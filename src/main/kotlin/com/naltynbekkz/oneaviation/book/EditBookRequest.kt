package com.naltynbekkz.oneaviation.book

data class EditBookRequest(
    val id: Long,
    val passengers: List<Long>,
)
