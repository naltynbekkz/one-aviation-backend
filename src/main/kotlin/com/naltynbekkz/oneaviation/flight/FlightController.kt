package com.naltynbekkz.oneaviation.flight

import com.naltynbekkz.oneaviation.book.ConfirmBookRequest
import com.naltynbekkz.oneaviation.ticket.TicketRepository
import com.naltynbekkz.oneaviation.util.SessionManager
import com.naltynbekkz.oneaviation.util.entity.Role
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/flights")
class FlightController(
    private val flightRepository: FlightRepository,
    private val ticketRepository: TicketRepository,
    private val sessionManager: SessionManager,
) {

    @GetMapping
    fun getAllFlights(
        @RequestHeader(value = "Authorization", required = false) tokenId: String?,
        @RequestParam("from") from: Long,
        @RequestParam("to") to: Long,
        response: HttpServletResponse,
    ): List<Flight> {
        sessionManager.getToken(tokenId, response, listOf(Role.MANAGER, Role.ADMIN))
        return flightRepository.getAll(from, to, FlightStatus.BOOKING).map {
            it.toFlight(ticketRepository.getByFlightId(it.id!!.toInt()))
        }
    }

    @GetMapping("/{id}")
    fun getFlight(
        @RequestHeader(value = "Authorization", required = false) tokenId: String?,
        @PathVariable id: Long,
        response: HttpServletResponse,
    ): Flight {
        sessionManager.getToken(tokenId, response, listOf(Role.MANAGER, Role.ADMIN))
        return flightRepository.findById(id).get().toFlight()
    }

    @PutMapping("/{id}")
    fun updateFlight(
        @RequestHeader(value = "Authorization", required = false) tokenId: String?,
        @PathVariable id: Long,
        @RequestBody request: UpdateFlightRequest,
        response: HttpServletResponse,
    ): Flight {
        sessionManager.getToken(tokenId, response, listOf(Role.MANAGER, Role.ADMIN))
        val flight = flightRepository.findById(id).get()
        flight.status = request.flightStatus
        return flightRepository.save(flight).toFlight()
    }

}