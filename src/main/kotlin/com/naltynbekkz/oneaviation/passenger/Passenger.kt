package com.naltynbekkz.oneaviation.passenger

import com.naltynbekkz.oneaviation.auth.User

class Passenger(
    val id: Long? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var documentId: String? = null,
    var user: User? = null,
)