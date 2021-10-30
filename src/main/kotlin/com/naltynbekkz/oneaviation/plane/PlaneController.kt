package com.naltynbekkz.oneaviation.plane

import com.naltynbekkz.oneaviation.util.SessionManager
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

        // TODO:
        //  check if the user has authority,
        //  save the plane to database,
        //  return the plane

        return Plane()
    }

    @GetMapping
    fun getAllPlanes(
        response: HttpServletResponse,
    ): List<Plane> {

        // TODO:
        // anyone can access the planes
        // get all planes from database
        // return the list

        return listOf()
    }

    @DeleteMapping("/{id}")
    fun deletePlane(
        @RequestHeader(value = "Authorization", required = false) tokenId: String?,
        @PathVariable id: Long,
        response: HttpServletResponse,
    ) {

        // TODO:
        //  check if the user has authority,
        //  delete from database,

    }

}