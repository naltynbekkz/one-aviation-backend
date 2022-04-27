package com.naltynbekkz.oneaviation.pilot

import com.naltynbekkz.oneaviation.util.SessionManager
import com.naltynbekkz.oneaviation.util.entity.Page
import com.naltynbekkz.oneaviation.util.entity.Role
import com.naltynbekkz.oneaviation.util.pageParams
import com.naltynbekkz.oneaviation.util.toPage
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/pilots")
class PilotController(
    private val pilotRepository: PilotRepository,
    private val sessionManager: SessionManager,
) {

    @PostMapping
    fun createPilot(
        @RequestHeader(value = "Authorization", required = false) tokenId: String?,
        @RequestBody request: CreatePilotRequest,
        response: HttpServletResponse,
    ): Pilot {
        val token = sessionManager.getToken(tokenId, response, listOf(Role.MANAGER))
        val pilot = PilotEntity(
            id = null,
            firstName = request.firstName,
            lastName = request.lastName,
            profession = request.profession,
        )
        return pilotRepository.save(pilot).toPilot()
    }

    @GetMapping
    fun getAllPilots(
        @RequestParam(value = "page", required = false) page: Int?,
        @RequestParam(value = "size", required = false) size: Int?,
        response: HttpServletResponse,
    ): Page<Pilot> {
        return pilotRepository.getNotDeleted(pageParams(page, size)).toPage { it.toPilot() }
    }

    @PutMapping
    fun putPilot(
        @RequestHeader(value = "Authorization", required = false) tokenId: String?,
        @RequestBody request: Pilot,
        response: HttpServletResponse,
    ): Pilot {
        sessionManager.getToken(tokenId, response, listOf(Role.MANAGER))
        val oldPilot = pilotRepository.findById(request.id).get()

        oldPilot.id = request.id
        oldPilot.firstName = request.firstName
        oldPilot.lastName = request.lastName
        oldPilot.profession = request.profession
        oldPilot.timestamp.update()

        return pilotRepository.save(oldPilot).toPilot()
    }

    @DeleteMapping("/{id}")
    fun deletePilot(
        @RequestHeader(value = "Authorization", required = false) tokenId: String?,
        @PathVariable id: Int,
        response: HttpServletResponse,
    ) {
        val pilot = pilotRepository.findById(id).get()
        sessionManager.getToken(tokenId, response, listOf(Role.MANAGER))
        pilot.timestamp.delete()
        pilotRepository.save(pilot)
    }

}