package com.naltynbekkz.oneaviation.ticket

import com.naltynbekkz.oneaviation.passenger.Passenger
import com.naltynbekkz.oneaviation.util.SessionManager
import com.naltynbekkz.oneaviation.util.entity.Page
import com.naltynbekkz.oneaviation.util.pageParams
import com.naltynbekkz.oneaviation.util.toPage
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/tickets")
class TicketController(
    private val ticketRepository: TicketRepository,
    private val sessionManager: SessionManager,
) {

    @GetMapping
    fun getMyTickets(
        @RequestHeader(value = "Authorization", required = false) tokenId: String?,
        @RequestParam(value = "page", required = false) page: Int?,
        @RequestParam(value = "size", required = false) size: Int?,
        response: HttpServletResponse,
    ): Page<Ticket> {

        val token = sessionManager.getToken(tokenId, response)

        return ticketRepository.getNotDeleted(token.user!!.id, pageParams(page, size))
            .toPage {
                it.toTicket()
            }
    }

}