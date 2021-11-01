package com.naltynbekkz.oneaviation.token

import com.naltynbekkz.oneaviation.util.entity.Timestamp

data class Token constructor(
    var id: Long,
    var uuid: String,
    var timestamp: Timestamp,
)