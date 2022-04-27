package com.naltynbekkz.oneaviation.passenger

import com.naltynbekkz.oneaviation.util.entity.Timestamp

class Passenger(
    val id: Int,
    var firstName: String,
    var lastName: String,
    var documentId: String,
    var timestamp: Timestamp,
)