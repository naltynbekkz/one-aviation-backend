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
        val tokenEntity: TokenEntity = sessionManager.getToken(tokenId, response)
        return tokenRepository.findByUser(tokenEntity.user!!, pageParams(page, size)).toPage {
            it.toToken()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteToken(
        @RequestHeader(value = "Authorization", required = false) tokenId: String?,
        @PathVariable id: Int,
        response: HttpServletResponse,
    ) {
        val tokenEntity: TokenEntity = sessionManager.getToken(tokenId, response)
        val delete = tokenRepository.findById(id).get()
        if (tokenEntity.user!!.id != delete.user!!.id) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "Token isn't yours to delete")
        }
        response.status = 204
        tokenRepository.deleteById(delete.id!!)
    }

}