package com.naltynbekkz.oneaviation.passenger

import com.naltynbekkz.oneaviation.util.SessionManager
import com.naltynbekkz.oneaviation.util.entity.Page
import com.naltynbekkz.oneaviation.util.pageParams
import com.naltynbekkz.oneaviation.util.toPage
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/passengers")
class PassengerController(
    private val passengerRepository: PassengerRepository,
    private val sessionManager: SessionManager,
) {

    @PostMapping
    fun createPassenger(
        @RequestHeader(value = "Authorization", required = false) tokenId: String?,
        @RequestBody request: CreatePassengerRequest,
        response: HttpServletResponse,
    ): Passenger {
        val token = sessionManager.getToken(tokenId, response)

        val passenger = PassengerEntity(
            id = null,
            firstName = request.firstName,
            lastName = request.lastName,
            documentId = request.documentId,
            user = token.user,
        )

        return passengerRepository.save(passenger).toPassenger()
    }

    @GetMapping
    fun getMyPassengers(
        @RequestHeader(value = "Authorization", required = false) tokenId: String?,
        @RequestParam(value = "page", required = false) page: Int?,
        @RequestParam(value = "size", required = false) size: Int?,
        response: HttpServletResponse,
    ): Page<Passenger> {

        val token = sessionManager.getToken(tokenId, response)

        return passengerRepository.getNotDeleted(token.user!!.id, pageParams(page, size))
            .toPage {
                it.toPassenger()
            }
    }

    @DeleteMapping("/{id}")
    fun deletePassenger(
        @RequestHeader(value = "Authorization", required = false) tokenId: String?,
        @PathVariable id: Long,
        response: HttpServletResponse,
    ) {
        val token = sessionManager.getToken(tokenId, response)
        val passenger = passengerRepository.findById(id).get()
        if (token.user!!.id != passenger.user!!.id) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "Passenger isn't yours to delete")
        }
        passenger.timestamp.delete()
        passengerRepository.save(passenger)
        response.status = 204
    }

}