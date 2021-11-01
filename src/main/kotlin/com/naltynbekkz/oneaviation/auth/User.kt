package com.naltynbekkz.oneaviation.auth

import com.fasterxml.jackson.annotation.JsonIgnore
import com.naltynbekkz.oneaviation.util.entity.Timestamp
import javax.persistence.*

@Entity
class User constructor(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(unique = true)
    var username: String? = null,

    var firstName: String? = null,
    var lastName: String? = null,

    @JsonIgnore
    var hashedPassword: String? = null,

    @Column(nullable = false)
    var role: Role? = null,

    @Embedded
    var timestamp: Timestamp = Timestamp(),

    @Transient
    var password: String? = null,
)