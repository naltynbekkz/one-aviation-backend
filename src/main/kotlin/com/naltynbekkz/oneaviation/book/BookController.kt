package com.naltynbekkz.oneaviation.book

import com.naltynbekkz.oneaviation.flight.FlightEntity
import com.naltynbekkz.oneaviation.flight.FlightRepository
import com.naltynbekkz.oneaviation.flight.FlightStatus
import com.naltynbekkz.oneaviation.passenger.PassengerRepository
import com.naltynbekkz.oneaviation.plane.PlaneRepository
import com.naltynbekkz.oneaviation.ticket.TicketEntity
import com.naltynbekkz.oneaviation.ticket.TicketRepository
import com.naltynbekkz.oneaviation.util.SessionManager
import com.naltynbekkz.oneaviation.util.entity.Role
import com.naltynbekkz.oneaviation.util.entity.Timestamp
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/book")
class BookController(
    private val sessionManager: SessionManager,
    private val passengerRepository: PassengerRepository,
    private val flightRepository: FlightRepository,
    private val ticketRepository: TicketRepository,
    private val bookRepository: BookRepository,
    private val planeRepository: PlaneRepository,
) {

    @GetMapping("/{id}")
    fun getBook(
        @RequestHeader(value = "Authorization", required = false) tokenId: String?,
        @PathVariable id: Long,
        response: HttpServletResponse,
    ): Book {
        sessionManager.getToken(tokenId, response, listOf(Role.CUSTOMER))
        return bookRepository.findById(id).get().toBook()
    }

    @PutMapping
    fun addPassengers(
        @RequestHeader(value = "Authorization", required = false) tokenId: String?,
        @RequestBody request: EditBookRequest,
        response: HttpServletResponse,
    ): Book {

        sessionManager.getToken(tokenId, response, listOf(Role.CUSTOMER))

        val passengers = request.passengers.map {
            passengerRepository.findById(it).get()
        }

        val book = bookRepository.findById(request.id).get()

        book.passengers = passengers

        return bookRepository.save(book).toBook()
    }

    @PostMapping
    fun confirm(
        @RequestHeader(value = "Authorization", required = false) tokenId: String?,
        @RequestBody request: ConfirmBookRequest,
        response: HttpServletResponse,
    ): ConfirmBookResponse {
        val token = sessionManager.getToken(tokenId, response, listOf(Role.CUSTOMER))

        val book = bookRepository.findById(request.id).get()

        val plane = planeRepository.findAll()[0]

        val flight = flightRepository.save(
            FlightEntity(
                plane = plane,
                timestamp = Timestamp(),
                departure = book.departure,
                arrival = book.arrival,
                status = FlightStatus.CREATED,
            )
        )

        val tickets = ticketRepository.saveAll(
            book.passengers!!.map {
                TicketEntity(
                    flight = flight,
                    passenger = it,
                    timestamp = Timestamp(),
                )
            }
        )

        return ConfirmBookResponse(flight.toFlight(), tickets.map { it.toTicket() })

    }

}