package com.naltynbekkz.oneaviation.user

import com.naltynbekkz.oneaviation.auth.RegistrationRequest
import com.naltynbekkz.oneaviation.plane.CreatePlaneRequest
import com.naltynbekkz.oneaviation.plane.Plane
import com.naltynbekkz.oneaviation.plane.PlaneEntity
import com.naltynbekkz.oneaviation.util.HashUtils
import com.naltynbekkz.oneaviation.util.SessionManager
import com.naltynbekkz.oneaviation.util.entity.Role
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/users")
class UserController(
    private val repository: UserRepository,
    private val sessionManager: SessionManager,
    private val hashUtils: HashUtils,
) {

    @GetMapping("/admins")
    fun getAdmins(
        @RequestHeader(value = "Authorization", required = false) tokenId: String?,
        response: HttpServletResponse,
    ): List<User> {
        sessionManager.getToken(tokenId, response, listOf(Role.MANAGER))
        return repository.findAdmins().map { it.toUser() }
    }

    @PostMapping
    fun createAdmin(
        @RequestHeader(value = "Authorization", required = false) tokenId: String?,
        @RequestBody request: RegistrationRequest,
        response: HttpServletResponse,
    ): User {
        val token = sessionManager.getToken(tokenId, response, listOf(Role.MANAGER))
        if (repository.findByUsername(request.username) != null) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "Email already exists")
        }
        val userEntity = UserEntity(
            username = request.username,
            firstName = request.firstName,
            lastName = request.lastName,
            role = Role.ADMIN,
            hashedPassword = hashUtils.hash(request.password.toCharArray())
        )
        return userEntity.toUser()
    }

}