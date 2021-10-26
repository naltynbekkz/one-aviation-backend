package com.naltynbekkz.oneaviation.token

import com.naltynbekkz.oneaviation.util.SessionManager
import com.naltynbekkz.oneaviation.util.entity.Page
import com.naltynbekkz.oneaviation.util.pageParams
import com.naltynbekkz.oneaviation.util.toPage
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/tokens")
class TokenController(
    private val tokenRepository: TokenRepository,
    private val sessionManager: SessionManager,
) {

    @GetMapping
    fun findAllTokens(
        @RequestHeader(value = "Authorization", required = false) tokenId: String?,
        @RequestParam(value = "page", required = false) page: Int?,
        @RequestParam(value = "size", required = false) size: Int?,
        response: HttpServletResponse,
    ): Page<Token> {
        val token: Token = sessionManager.getToken(tokenId, response, listOf())
        return tokenRepository.findByUser(token.user!!, pageParams(page, size)).toPage()
    }

    @DeleteMapping("/{id}")
    fun deleteToken(
        @RequestHeader(value = "Authorization", required = false) tokenId: String?,
        @PathVariable id: Long,
        response: HttpServletResponse,
    ) {
        val token: Token = sessionManager.getToken(tokenId, response, listOf())
        val delete = tokenRepository.findById(id).get()
        if (token.user!!.id != delete.user!!.id) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "Token isn't yours to delete")
        }
        response.status = 204
        return tokenRepository.deleteById(delete.id!!)
    }

}