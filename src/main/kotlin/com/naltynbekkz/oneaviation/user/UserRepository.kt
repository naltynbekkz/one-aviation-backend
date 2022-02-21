package com.naltynbekkz.oneaviation.user

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByUsername(email: String): UserEntity?

    @Query(
        value = "select * from users where timestamp_deleted is null and role = 'Customer'",
        nativeQuery = true
    )
    fun findCustomers(pageable: Pageable): Page<UserEntity>
}