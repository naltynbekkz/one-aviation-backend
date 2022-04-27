package com.naltynbekkz.oneaviation.flight

import com.naltynbekkz.oneaviation.passenger.PassengerRepository
import com.naltynbekkz.oneaviation.plane.PlaneRepository
import com.naltynbekkz.oneaviation.ticket.TicketEntity
import com.naltynbekkz.oneaviation.ticket.TicketRepository
import com.naltynbekkz.oneaviation.util.SessionManager
import com.naltynbekkz.oneaviation.util.entity.Role
import com.naltynbekkz.oneaviation.util.entity.Timestamp
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import javax.servlet.http.HttpServletResponse
import kotlin.time.ExperimentalTime

@RestController
@RequestMapping("/flights")
class FlightController(
    private val flightRepository: FlightRepository,
    private val ticketRepository: TicketRepository,
    private val sessionManager: SessionManager,
    private val passengerRepository: PassengerRepository,
    private val planeRepository: PlaneRepository,
) {

    @GetMapping
    fun getAllFlights(
        @RequestHeader(value = "Authorization", required = false) tokenId: String?,
        @RequestParam("from") from: Long,
        @RequestParam("to") to: Long,
        response: HttpServletResponse,
    ): List<LogsResponse> {
        sessionManager.getToken(tokenId, response, listOf(Role.MANAGER, Role.ADMIN))

        val flights = flightRepository.getAll(from, to).map {
            val tickets = ticketRepository.getTicketsByFlightId(it.id!!).map { it.toTicket() }
            it.toFlight(tickets.filter { it.timestamp.deleted == null }.size)
        }.groupBy { it.plane.id }

        val planes = planeRepository.getNotDeleted().map { it.toPlane() }

        return planes.map {
            LogsResponse(it, flights[it.id] ?: listOf())
        }
    }

    @GetMapping("/{id}")
    fun getFlight(
        @RequestHeader(value = "Authorization", required = false) tokenId: String?,
        @PathVariable id: Int,
        response: HttpServletResponse,
    ): Flight {
        val token = sessionManager.getToken(tokenId, response)
        val tickets = ticketRepository.getTicketsByFlightId(id).map { it.toTicket() }
        return flightRepository.findById(id).get()
            .toFlight(
                tickets.filter { it.timestamp.deleted == null }.size,
                tickets.takeUnless { token.user?.role == Role.CUSTOMER }.orEmpty()
            )
    }

    @PutMapping("/{id}")
    fun updateFlight(
        @RequestHeader(value = "Authorization", required = false) tokenId: String?,
        @PathVariable id: Int,
        @RequestBody request: UpdateFlightRequest,
        response: HttpServletResponse,
    ): Flight {
        sessionManager.getToken(tokenId, response, listOf(Role.MANAGER, Role.ADMIN))
        val flight = flightRepository.findById(id).get()
        flight.status = request.flightStatus
        if (request.flightStatus == FlightStatus.CANCELLED) {
            // TODO: send notifications
        }
        return flightRepository.save(flight).toFlight()
    }

    @PostMapping
    fun addPassengers(
        @RequestHeader(value = "Authorization", required = false) tokenId: String?,
        @RequestBody request: ConfirmBookRequest,
        response: HttpServletResponse,
    ): ConfirmBookResponse {

        sessionManager.getToken(tokenId, response, listOf(Role.CUSTOMER))

        val flightEntity = flightRepository.findById(request.id).get()
        val flight = flightEntity.toFlight()

        if (flight.availableSeats() < request.passengers.size) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Too many passengers")
        }

        val tickets = ticketRepository.saveAll(
            request.passengers.map {
                val passenger = passengerRepository.findById(it).get()
                TicketEntity(
                    flight = flightEntity,
                    passenger = passenger,
                    timestamp = Timestamp(),
                )
            }
        )

        return ConfirmBookResponse(flight, tickets.map { it.toTicket() })
    }

    @OptIn(ExperimentalTime::class)
    @PostMapping("/similar")
    fun getSimilarFlights(
        @RequestHeader(value = "Authorization", required = false) tokenId: String?,
        @RequestBody request: FlightSearchParams,
        response: HttpServletResponse,
    ): List<Flight> {
        sessionManager.getToken(tokenId, response)

        val flights = flightRepository.getSimilar(
            arrivalLat = request.arrival.lat!!,
            arrivalLong = request.arrival.long!!,
            departureLat = request.departure.location!!.lat!!,
            departureLong = request.departure.location.long!!,
            departureTime = request.departure.time!!,
            maxDistanceDifference = 100f, // TODO: tweak
            maxTimeDifference = 5 * 60 * 60 * 1000L, // TODO: tweak
        )

        return flights.map { it.toFlight() }
    }

    @PostMapping("/book")
    fun initialBooking(
        @RequestHeader(value = "Authorization", required = false) tokenId: String?,
        @RequestBody request: FlightSearchParams,
        response: HttpServletResponse,
    ): Flight {
        sessionManager.getToken(tokenId, response, listOf(Role.CUSTOMER))

        // TODO find a plane that has enough seats, and is available at that time and place.
        val plane = planeRepository.findAll().random()

        val flight = FlightEntity(
            id = null,
            plane = plane,
            timestamp = Timestamp(),
            departure = request.departure,
            arrival = request.arrival,
            status = FlightStatus.BOOKING,
        )

        return flightRepository.save(flight).toFlight()
    }

    @GetMapping("/book/{id}")
    fun bookByFlightId(
        @RequestHeader(value = "Authorization", required = false) tokenId: String?,
        @PathVariable id: Int,
        response: HttpServletResponse,
    ): Flight {
        sessionManager.getToken(tokenId, response, listOf(Role.CUSTOMER))
        return flightRepository.findById(id).get().toFlight()
    }

}