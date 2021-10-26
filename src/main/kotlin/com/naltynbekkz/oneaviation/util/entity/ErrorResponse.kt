package com.naltynbekkz.oneaviation.util.entity

class ErrorResponse(
    var timestamp: Long,
    var status: Int,
    var error: String,
    var message: String?,
    var path: String
)