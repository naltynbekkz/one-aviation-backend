package com.naltynbekkz.oneaviation.ticket

import com.naltynbekkz.oneaviation.util.SessionManager
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/tickets")
class TicketController(
    private val ticketRepository: TicketRepository,
    private val sessionManager: SessionManager,
) {


}