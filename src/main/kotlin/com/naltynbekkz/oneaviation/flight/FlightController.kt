package com.naltynbekkz.oneaviation.flight

import com.naltynbekkz.oneaviation.util.SessionManager
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/flights")
class FlightController(
    private val flightRepository: FlightRepository,
    private val sessionManager: SessionManager,)
{
    
    @PostMapping
    fun createFlight(
        @RequestHeader(value = "Authorization", required = false) tokenId: String?,
        @RequestBody request: CreatePlaneRequest,
        response: HttpServletResponse,
    ): Flight {
        val token = sessionManager.getToken(tokenId, response, listOf(Role.MANAGER))
        val flight = PlaneEntity(
            id = null,
            plane = request.plane,
            timestamp = request.timestamp,
            departure = request.departure,
            arrival = request.arrival,
            
            
//     author = token.user
//     val id: Long,
//     val plane: Plane,
//     val timestamp: Timestamp,
//     val departure: Info,
//     val arrival: Info,
        )
        return flightRepository.save(plane).toFlight()
    }

    @GetMapping
    fun getAllFlights(
        @RequestParam(value = "page", required = false) page: Int?,
        @RequestParam(value = "size", required = false) size: Int?,
        response: HttpServletResponse,
    ): Page<Flight> {
        return flightRepository.getNotDeleted(pageParams(page, size)).toPage { it.toFlight() }
    }

    @PutMapping
    fun putFlight(
        @RequestHeader(value = "Authorization", required = false) tokenId: String?,
        @RequestBody request: Flight,
        response: HttpServletResponse,
    ): Flight {

        val oldPlane = planeRepository.findById(request.id).get()
        val token = sessionManager.getToken(tokenId, response, listOf(Role.MANAGER))

        oldFlight.id = request.id
        oldFlight.plane = request.plane
        oldFlight.timestamp = request.timestamp
        oldFlight.departure = request.departure
        oldFlight.arrival = request.arrival
        oldFlight.timestamp.update()
        
//         id = null,
//         plane = request.plane,
//         timestamp = request.timestamp,
//         departure = request.departure,
//         arrival = request.arrival,
        

        return flightRepository.save(oldFlight).toFlight()
    }

    @DeleteMapping("/{id}")
    fun deleteFlight(
        @RequestHeader(value = "Authorization", required = false) tokenId: String?,
        @PathVariable id: Long,
        response: HttpServletResponse,
    ) {
        val flight = planeRepository.findById(id).get()
        sessionManager.getToken(tokenId, response, listOf(Role.MANAGER))
        flight.timestamp.delete()
        flightRepository.save(flight)
    }

}
