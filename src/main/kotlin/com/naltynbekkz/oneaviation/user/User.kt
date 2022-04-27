package com.naltynbekkz.oneaviation.user

import com.naltynbekkz.oneaviation.util.entity.Timestamp

data class User(
    var id: Int,
    var username: String,
    var firstName: String,
    var lastName: String,
    var timestamp: Timestamp = Timestamp(),
)