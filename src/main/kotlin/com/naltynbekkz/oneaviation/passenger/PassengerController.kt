package com.naltynbekkz.oneaviation.passenger

import com.naltynbekkz.oneaviation.util.SessionManager
import org.springframework.web.bind.annotation.*
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

        // TODO:
        //  get the user,
        //  save the passenger to database,
        //  return the passenger

        return Passenger()
    }

    @GetMapping
    fun getMyPassengers(
        @RequestHeader(value = "Authorization", required = false) tokenId: String?,
        response: HttpServletResponse,
    ): List<Passenger> {

        // TODO:
        // get the user
        // get all of the user's passengers from database
        // return the list

        return listOf()
    }

    @DeleteMapping("/{id}")
    fun deletePassenger(
        @RequestHeader(value = "Authorization", required = false) tokenId: String?,
        @PathVariable id: Long,
        response: HttpServletResponse,
    ) {

        // TODO:
        //  check if the user has authority,
        //  delete from database,

    }

}