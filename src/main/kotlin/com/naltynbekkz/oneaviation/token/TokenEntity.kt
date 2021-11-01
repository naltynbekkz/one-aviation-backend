package com.naltynbekkz.oneaviation.token

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.naltynbekkz.oneaviation.auth.User
import com.naltynbekkz.oneaviation.util.entity.Timestamp
import javax.persistence.*

@Entity
@JsonIgnoreProperties("hibernateLazyInitializer", "handler")
class TokenEntity constructor(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(unique = true, nullable = false)
    var uuid: String? = null,

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    var user: User? = null,

    // A token can change password in the first Constants.SUDO_TOKEN_TIME milliseconds
    @Embedded
    var timestamp: Timestamp = Timestamp()
) {
    fun toToken(): Token {
        return Token(id!!, uuid!!, timestamp)
    }
}