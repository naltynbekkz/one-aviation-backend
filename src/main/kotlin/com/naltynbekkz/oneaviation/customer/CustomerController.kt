package com.naltynbekkz.oneaviation.customer

import com.naltynbekkz.oneaviation.user.User
import com.naltynbekkz.oneaviation.user.UserEntity
import com.naltynbekkz.oneaviation.user.UserRepository
import com.naltynbekkz.oneaviation.util.SessionManager
import com.naltynbekkz.oneaviation.util.entity.Page
import com.naltynbekkz.oneaviation.util.entity.Role
import com.naltynbekkz.oneaviation.util.pageParams
import com.naltynbekkz.oneaviation.util.toPage
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/customers")
class CustomerController(
    private val userRepository: UserRepository,
    private val sessionManager: SessionManager,
) {

    @GetMapping
    fun getAllCustomers(
        @RequestHeader(value = "Authorization", required = false) tokenId: String?,
        @RequestParam(value = "page", required = false) page: Int?,
        @RequestParam(value = "size", required = false) size: Int?,
        response: HttpServletResponse,
    ): Page<User> {
        sessionManager.getToken(tokenId, response, listOf(Role.MANAGER))
        return userRepository.findCustomers(pageParams(page, size)).toPage { it.toUser() }
    }

    @PostMapping
    fun createCustomer(
        @RequestHeader(value = "Authorization", required = false) tokenId: String?,
        @RequestBody request: CreateCustomerRequest,
        response: HttpServletResponse,
    ): User {
        sessionManager.getToken(tokenId, response, listOf(Role.MANAGER))
        val user = UserEntity(
            id = null,
            firstName = request.firstName,
            lastName = request.lastName,
            role = Role.CUSTOMER,
        )
        return userRepository.save(user).toUser()
    }

    @DeleteMapping("/{id}")
    fun deletePassenger(
        @RequestHeader(value = "Authorization", required = false) tokenId: String?,
        @PathVariable id: Int,
        response: HttpServletResponse,
    ) {
        sessionManager.getToken(tokenId, response, listOf(Role.MANAGER))
        val passenger = userRepository.findById(id).get()
        passenger.timestamp.delete()
        userRepository.save(passenger)
        response.status = 204
    }

}