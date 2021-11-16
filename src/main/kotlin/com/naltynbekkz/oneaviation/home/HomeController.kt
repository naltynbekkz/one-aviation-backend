package com.naltynbekkz.oneaviation.home

import com.naltynbekkz.oneaviation.book.Book
import com.naltynbekkz.oneaviation.book.BookEntity
import com.naltynbekkz.oneaviation.book.BookRepository
import com.naltynbekkz.oneaviation.flight.Flight
import com.naltynbekkz.oneaviation.util.SessionManager
import com.naltynbekkz.oneaviation.util.entity.Role
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/home")
class HomeController(
    private val repository: BookRepository,
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

    @PostMapping("/book")
    fun initialBooking(
        @RequestHeader(value = "Authorization", required = false) tokenId: String?,
        @RequestBody request: FlightSearchParams,
        response: HttpServletResponse,
    ): Book {
        val token = sessionManager.getToken(tokenId, response, listOf(Role.CUSTOMER))

        val book = BookEntity(
            null,
            request.passengerCount,
            request.departure,
            request.arrival,
            listOf(),
            token.user
        )

        return repository.save(book).toBook()
    }

}