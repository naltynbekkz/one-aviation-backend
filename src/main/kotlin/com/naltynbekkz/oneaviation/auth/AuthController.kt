package com.naltynbekkz.oneaviation.auth

import com.naltynbekkz.oneaviation.token.Token
import com.naltynbekkz.oneaviation.token.TokenRepository
import com.naltynbekkz.oneaviation.util.HashUtils
import com.naltynbekkz.oneaviation.util.SessionManager
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/auth")
class AuthController(
    private val userRepository: UserRepository,
    private val tokenRepository: TokenRepository,
    private val sessionManager: SessionManager,
    private val hashUtils: HashUtils,
) {

    @PostMapping("/register")
    fun register(
        @RequestBody registerRequest: User,
        response: HttpServletResponse,
    ): Token {
        if (userRepository.findByUsername(registerRequest.username!!) != null) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "Email already exists")
        }

        registerRequest.hashedPassword = hashUtils.hash(registerRequest.password!!.toCharArray())
        val user = User(registerRequest)

        return sessionManager.login(userRepository.save(user), response)
    }

    @PostMapping("/login")
    fun login(
        @RequestBody loginRequest: User,
        response: HttpServletResponse,
    ): Token {
        val existingUser = userRepository.findByUsername(loginRequest.username!!)!!
        if (!hashUtils.authenticate(loginRequest.password!!, existingUser.hashedPassword!!)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong email or password")
        }
        return sessionManager.login(existingUser, response)
    }

    @DeleteMapping("/logout")
    fun logout(
        @RequestHeader(value = "Authorization", required = false) tokenId: String?,
        response: HttpServletResponse,
    ) {
        val token = sessionManager.getToken(tokenId, response, emptyList())
        token.timestamp.delete()
        tokenRepository.save(token)
        response.status = HttpStatus.NO_CONTENT.value()
    }

    @PutMapping("changePassword")
    fun changePassword(
        @RequestHeader(value = "Authorization", required = false) tokenId: String?,
        @RequestBody changePassword: ChangePassword,
        response: HttpServletResponse,
    ): Token {
        val token = sessionManager.getToken(tokenId, response, listOf())
        if (token.timestamp.created + 10 * 60 * 1000 < System.currentTimeMillis()) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "Token is too old")
        }
        tokenRepository.deleteByUser(token.user!!)
        token.user!!.hashedPassword = hashUtils.hash(changePassword.newPassword.toCharArray())
        userRepository.save(token.user!!)
        return sessionManager.login(token.user!!, response)
    }

}