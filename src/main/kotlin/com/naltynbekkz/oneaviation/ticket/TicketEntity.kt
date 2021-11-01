package com.naltynbekkz.oneaviation.ticket

import com.naltynbekkz.oneaviation.flight.FlightEntity
import com.naltynbekkz.oneaviation.passenger.PassengerEntity
import com.naltynbekkz.oneaviation.util.entity.Timestamp
import javax.persistence.*

@Entity
@Table(name = "tickets")
class TicketEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    val flight: FlightEntity? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    val passenger: PassengerEntity? = null,

    @Embedded
    val timestamp: Timestamp = Timestamp(),
) {
    fun toTicket() = Ticket(id!!, flight!!.toFlight(), passenger!!.toPassenger(), passenger.user!!.toUser(), timestamp)
}