package com.naltynbekkz.oneaviation.flight

import com.naltynbekkz.oneaviation.ticket.Ticket

data class ConfirmBookResponse(
    val flight: Flight,
    val tickets: List<Ticket>
)
