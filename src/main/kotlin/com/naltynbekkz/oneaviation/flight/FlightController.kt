package com.naltynbekkz.oneaviation.flight

import com.naltynbekkz.oneaviation.util.SessionManager
import com.naltynbekkz.oneaviation.util.entity.Role
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/flights")
class FlightController(
    private val flightRepository: FlightRepository,
    private val sessionManager: SessionManager,
) {

    @GetMapping
    fun getAllFlights(
        @RequestHeader(value = "Authorization", required = false) tokenId: String?,
        response: HttpServletResponse,
    ): List<Flight> {
        sessionManager.getToken(tokenId, response, listOf(Role.MANAGER, Role.ADMIN))
        return flightRepository.getAll().map { it.toFlight() }
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

}