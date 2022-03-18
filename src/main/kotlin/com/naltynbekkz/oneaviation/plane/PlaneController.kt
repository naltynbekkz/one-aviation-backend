package com.naltynbekkz.oneaviation.plane

import com.naltynbekkz.oneaviation.util.SessionManager
import com.naltynbekkz.oneaviation.util.entity.Role
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/planes")
class PlaneController(
    private val planeRepository: PlaneRepository,
    private val sessionManager: SessionManager,
) {

    @PostMapping
    fun createPlane(
        @RequestHeader(value = "Authorization", required = false) tokenId: String?,
        @RequestBody request: CreatePlaneRequest,
        response: HttpServletResponse,
    ): Plane {
        val token = sessionManager.getToken(tokenId, response, listOf(Role.MANAGER))
        val plane = PlaneEntity(
            id = null,
            name = request.name,
            mileage = request.mileage,
            capacity = request.capacity,
            price = request.price,
            author = token.user
        )
        return planeRepository.save(plane).toPlane()
    }

    @GetMapping
    fun getAllPlanes(
        @RequestParam(value = "page", required = false) page: Int?,
        @RequestParam(value = "size", required = false) size: Int?,
        response: HttpServletResponse,
    ): List<Plane> {
        return planeRepository.getNotDeleted().map { it.toPlane() }
    }

    @GetMapping("/{id}")
    fun getPlane(
        @RequestHeader(value = "Authorization", required = false) tokenId: String?,
        @PathVariable id: Long,
        response: HttpServletResponse,
    ): Plane {
        sessionManager.getToken(tokenId, response, listOf(Role.MANAGER))
        return planeRepository.findById(id).get().toPlane()
    }

    @PutMapping("/{id}")
    fun putPlane(
        @RequestHeader(value = "Authorization", required = false) tokenId: String?,
        @PathVariable id: Long,
        @RequestBody request: CreatePlaneRequest,
        response: HttpServletResponse,
    ): Plane {
        val oldPlane = planeRepository.findById(id).get()
        val token = sessionManager.getToken(tokenId, response, listOf(Role.MANAGER))

        oldPlane.name = request.name
        oldPlane.mileage = request.mileage
        oldPlane.capacity = request.capacity
        oldPlane.price = request.price
        oldPlane.author = token.user
        oldPlane.timestamp.update()

        return planeRepository.save(oldPlane).toPlane()
    }

    @DeleteMapping("/{id}")
    fun deletePlane(
        @RequestHeader(value = "Authorization", required = false) tokenId: String?,
        @PathVariable id: Long,
        response: HttpServletResponse,
    ) {
        val plane = planeRepository.findById(id).get()
        sessionManager.getToken(tokenId, response, listOf(Role.MANAGER))
        plane.timestamp.delete()
        planeRepository.save(plane)
    }

}