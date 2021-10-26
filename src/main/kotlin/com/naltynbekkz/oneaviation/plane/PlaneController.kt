package com.naltynbekkz.oneaviation.plane

import com.naltynbekkz.oneaviation.util.SessionManager
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/planes")
class PlaneController(
    private val planeRepository: PlaneRepository,
    private val sessionManager: SessionManager,
) {


}