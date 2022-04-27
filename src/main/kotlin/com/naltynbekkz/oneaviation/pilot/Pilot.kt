package com.naltynbekkz.oneaviation.pilot

import com.naltynbekkz.oneaviation.util.entity.Timestamp

class Pilot(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val profession: String,
    var timestamp: Timestamp,
)