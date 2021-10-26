package com.naltynbekkz.oneaviation.ticket

import com.naltynbekkz.oneaviation.auth.User
import com.naltynbekkz.oneaviation.flight.Flight
import com.naltynbekkz.oneaviation.passenger.Passenger
import com.naltynbekkz.oneaviation.util.entity.Timestamp
import javax.persistence.*

@Entity
class Ticket(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    val flight: Flight? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    val passenger: Passenger? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    val user: User? = null,

    @Embedded
    val timestamp: Timestamp = Timestamp(),
)