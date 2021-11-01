package com.naltynbekkz.oneaviation.token

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.naltynbekkz.oneaviation.user.UserEntity
import com.naltynbekkz.oneaviation.util.entity.Timestamp
import javax.persistence.*

@Entity
@Table(name = "tokens")
@JsonIgnoreProperties("hibernateLazyInitializer", "handler")
class TokenEntity constructor(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(unique = true, nullable = false)
    var uuid: String? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    var user: UserEntity? = null,

    @Embedded
    var timestamp: Timestamp = Timestamp()
) {
    fun toToken() = Token(id!!, uuid!!, timestamp)
}