package com.naltynbekkz.oneaviation.user

import com.naltynbekkz.oneaviation.token.Token
import com.naltynbekkz.oneaviation.token.TokenRepository
import com.naltynbekkz.oneaviation.util.HashUtils
import com.naltynbekkz.oneaviation.util.SessionManager
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/profile")
class ProfileController(
    private val userRepository: UserRepository,
    private val tokenRepository: TokenRepository,
    private val sessionManager: SessionManager,
    private val hashUtils: HashUtils,
) {

    @GetMapping
    fun getProfile(
        @RequestHeader(value = "Authorization", required = false) tokenId: String?,
        response: HttpServletResponse,
    ): User {
        val token = sessionManager.getToken(tokenId, response)
        return token.user!!.toUser()
    }

    @DeleteMapping("/logout")
    fun logout(
        @RequestHeader(value = "Authorization", required = false) tokenId: String?,
        response: HttpServletResponse,
    ) {
        val token = sessionManager.getToken(tokenId, response)
        token.timestamp.delete()
        tokenRepository.save(token)
        response.status = HttpStatus.NO_CONTENT.value()
    }

    @PutMapping("/changePassword")
    fun changePassword(
        @RequestHeader(value = "Authorization", required = false) tokenId: String?,
        @RequestBody changePassword: ChangePassword,
        response: HttpServletResponse,
    ): Token {
        val token = sessionManager.getToken(tokenId, response)
        val existingUser = token.user!!
        if (!hashUtils.authenticate(changePassword.oldPassword, existingUser.hashedPassword!!)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong email or password")
        }
        tokenRepository.deleteByUser(token.user!!)
        existingUser.hashedPassword = hashUtils.hash(changePassword.newPassword.toCharArray())
        userRepository.save(existingUser)
        return sessionManager.login(existingUser, response).toToken()
    }

}