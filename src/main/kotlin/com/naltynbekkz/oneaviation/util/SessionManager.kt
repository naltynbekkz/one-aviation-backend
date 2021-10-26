package com.naltynbekkz.oneaviation.util

import com.naltynbekkz.oneaviation.auth.Role
import com.naltynbekkz.oneaviation.auth.User
import com.naltynbekkz.oneaviation.token.Token
import com.naltynbekkz.oneaviation.token.TokenRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import javax.servlet.http.HttpServletResponse

@Service
class SessionManager(private val tokenRepository: TokenRepository) {

    fun getToken(
        bearerToken: String?,
        response: HttpServletResponse,
        roles: List<Role>,
    ): Token {
        val token = getTokenPrivate(
            bearerToken,
            response
        )
        if (roles.isNotEmpty() && !roles.contains(token.user!!.role)) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied")
        }
        return token
    }

    private fun getTokenPrivate(
        bearerToken: String?,
        response: HttpServletResponse,
    ): Token {
        if (bearerToken == null) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authorization header is null")
        }
        val token = tokenRepository.findByUuid(bearerToken.substring(7)).orElseThrow {
            ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token doesn't exist")
        }
        if (token.timestamp.deleted != null) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token has expired")
        }
        if (token.user == null) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Couldn't find the user")
        }
        setSetupHeaders(response, token.user!!)
        return token
    }

    /**
     * login
     *
     * @param user - this function isn't responsible for making sure the user is correct.
     * @return
     */
    fun login(user: User, response: HttpServletResponse): Token {
        var tokenUuid: String
        do {
            tokenUuid = generateString()
        } while (tokenRepository.existsByUuid(tokenUuid))
        val token = Token(
            uuid = tokenUuid,
            user = user
        )
        setSetupHeaders(response, user)
        return tokenRepository.save(token)
    }


    fun setSetupHeaders(response: HttpServletResponse, user: User) {
        response.setHeader("role", user.role!!.value)
    }
}