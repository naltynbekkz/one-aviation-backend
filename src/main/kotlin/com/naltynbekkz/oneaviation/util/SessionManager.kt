package com.naltynbekkz.oneaviation.util

import com.naltynbekkz.oneaviation.util.entity.Role
import com.naltynbekkz.oneaviation.user.UserEntity
import com.naltynbekkz.oneaviation.token.TokenEntity
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
        roles: List<Role> = listOf(),
    ): TokenEntity {

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

        if (roles.isNotEmpty() && !roles.contains(token.user!!.role)) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied")
        }
        return token
    }

    /**
     * login
     *
     * @param userEntity - this function isn't responsible for making sure the user is correct.
     * @return
     */
    fun login(userEntity: UserEntity, response: HttpServletResponse): TokenEntity {
        var tokenUuid: String
        do {
            tokenUuid = generateString()
        } while (tokenRepository.existsByUuid(tokenUuid))
        val tokenEntity = TokenEntity(
            uuid = tokenUuid,
            user = userEntity
        )
        setSetupHeaders(response, userEntity)
        return tokenRepository.save(tokenEntity)
    }


    fun setSetupHeaders(response: HttpServletResponse, userEntity: UserEntity) {
        response.setHeader("role", userEntity.role!!.value)
    }
}