package com.naltynbekkz.oneaviation.passenger

import com.naltynbekkz.oneaviation.util.SessionManager
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/passengers")
class PassengerController(
    private val passengerRepository: PassengerRepository,
    private val sessionManager: SessionManager,
) {


}