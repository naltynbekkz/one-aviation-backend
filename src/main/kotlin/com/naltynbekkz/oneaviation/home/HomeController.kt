package com.naltynbekkz.oneaviation.home

import com.naltynbekkz.oneaviation.flight.Flight
import com.naltynbekkz.oneaviation.flight.FlightRepository
import com.naltynbekkz.oneaviation.util.SessionManager
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse
import kotlin.random.Random

@RestController
@RequestMapping("/home")
class HomeController(
    private val flightRepository: FlightRepository,
    private val sessionManager: SessionManager,
) {

    @PostMapping("/similar")
    fun getSimilarFlights(
        @RequestHeader(value = "Authorization", required = false) tokenId: String?,
        @RequestBody request: FlightSearchParams,
        response: HttpServletResponse,
    ): List<Flight> {
        sessionManager.getToken(tokenId, response)

        return Flight.getRandomList()
    }

}