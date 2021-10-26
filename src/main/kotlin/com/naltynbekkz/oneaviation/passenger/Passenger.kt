package com.naltynbekkz.oneaviation.passenger

import com.naltynbekkz.oneaviation.auth.User
import javax.persistence.*

@Entity
class Passenger(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var firstName: String? = null,
    var lastName: String? = null,
    var documentId: String? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    var user: User? = null,
)