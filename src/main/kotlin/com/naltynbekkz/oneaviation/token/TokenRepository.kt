package com.naltynbekkz.oneaviation.token

import com.naltynbekkz.oneaviation.auth.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*
import javax.transaction.Transactional

@Repository
interface TokenRepository : JpaRepository<Token, Long> {
    fun findByUser(user: User, pageable: Pageable): Page<Token>

    @Transactional
    fun deleteByUser(user: User): Long

    fun findByUuid(value: String): Optional<Token>

    fun existsByUuid(value: String): Boolean

}