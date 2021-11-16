package com.naltynbekkz.oneaviation.book

import com.naltynbekkz.oneaviation.flight.Flight
import com.naltynbekkz.oneaviation.ticket.Ticket

data class ConfirmBookResponse(
    val flight: Flight,
    val tickets: List<Ticket>
)
