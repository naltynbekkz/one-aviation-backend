package com.naltynbekkz.oneaviation.token

import com.naltynbekkz.oneaviation.user.UserEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*
import javax.transaction.Transactional

@Repository
interface TokenRepository : JpaRepository<TokenEntity, Long> {
    fun findByUser(userEntity: UserEntity, pageable: Pageable): Page<TokenEntity>

    @Transactional
    fun deleteByUser(userEntity: UserEntity): Long

    fun findByUuid(value: String): Optional<TokenEntity>

    fun existsByUuid(value: String): Boolean

}