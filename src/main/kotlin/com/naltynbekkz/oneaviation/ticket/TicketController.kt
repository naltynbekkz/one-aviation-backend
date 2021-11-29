package com.naltynbekkz.oneaviation.ticket

import com.naltynbekkz.oneaviation.util.SessionManager
import com.naltynbekkz.oneaviation.util.entity.Page
import com.naltynbekkz.oneaviation.util.pageParams
import com.naltynbekkz.oneaviation.util.toPage
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/tickets")
class TicketController(
    private val repository: TicketRepository,
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

        return repository.getNotDeleted(token.user!!.id, pageParams(page, size))
            .toPage {
                it.toTicket()
            }
    }

    @DeleteMapping("/{id}")
    fun cancelTicket(
        @RequestHeader(value = "Authorization", required = false) tokenId: String?,
        @PathVariable id: Long,
        response: HttpServletResponse,
    ) {
        val token = sessionManager.getToken(tokenId, response)
        val entity = repository.findById(id).get()
        if (token.user!!.id != entity.passenger!!.user!!.id) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "Ticket isn't yours to delete")
        }
        entity.timestamp.delete()
        repository.save(entity)
        response.status = 204
    }

}