package com.naltynbekkz.oneaviation.auth

import com.naltynbekkz.oneaviation.token.Token
import com.naltynbekkz.oneaviation.user.UserEntity
import com.naltynbekkz.oneaviation.user.UserRepository
import com.naltynbekkz.oneaviation.util.HashUtils
import com.naltynbekkz.oneaviation.util.SessionManager
import com.naltynbekkz.oneaviation.util.entity.Role
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/auth")
class AuthController(
    private val userRepository: UserRepository,
    private val sessionManager: SessionManager,
    private val hashUtils: HashUtils,
) {

    @PostMapping("/register")
    fun register(
        @RequestBody registrationRequest: RegistrationRequest,
        response: HttpServletResponse,
    ): Token {
        if (userRepository.findByUsername(registrationRequest.username) != null) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "Email already exists")
        }
        val userEntity = UserEntity(
            username = registrationRequest.username,
            firstName = registrationRequest.firstName,
            lastName = registrationRequest.lastName,
            role = Role.CUSTOMER,
            hashedPassword = hashUtils.hash(registrationRequest.password.toCharArray())
        )
        return sessionManager.login(userRepository.save(userEntity), response).toToken()
    }

    @PostMapping("/login")
    fun login(
        @RequestBody loginRequest: LoginRequest,
        response: HttpServletResponse,
    ): Token {
        val existingUser = userRepository.findByUsername(loginRequest.username)!!
        if (!hashUtils.authenticate(loginRequest.password, existingUser.hashedPassword!!)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong email or password")
        }
        return sessionManager.login(existingUser, response).toToken()
    }

}