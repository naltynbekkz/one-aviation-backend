package com.naltynbekkz.oneaviation.ticket

import com.naltynbekkz.oneaviation.util.SessionManager
import com.naltynbekkz.oneaviation.util.entity.Page
import com.naltynbekkz.oneaviation.util.entity.Role
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

    @PutMapping("/{id}")
    fun uncancelTicket(
        @RequestHeader(value = "Authorization", required = false) tokenId: String?,
        @PathVariable id: Int,
        response: HttpServletResponse,
    ) {
        sessionManager.getToken(tokenId, response, listOf(Role.MANAGER, Role.ADMIN, Role.GOD))
        val entity = repository.findById(id).get()
        entity.timestamp.undelete()
        repository.save(entity)
        response.status = 204
    }

    @DeleteMapping("/{id}")
    fun cancelTicket(
        @RequestHeader(value = "Authorization", required = false) tokenId: String?,
        @PathVariable id: Int,
        response: HttpServletResponse,
    ) {
        sessionManager.getToken(tokenId, response, listOf(Role.MANAGER, Role.ADMIN, Role.GOD))
        val entity = repository.findById(id).get()
        entity.timestamp.delete()
        repository.save(entity)
        response.status = 204
    }

}