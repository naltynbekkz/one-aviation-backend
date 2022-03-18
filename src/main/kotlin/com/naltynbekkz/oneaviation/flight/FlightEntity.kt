package com.naltynbekkz.oneaviation.flight

import com.naltynbekkz.oneaviation.plane.PlaneEntity
import com.naltynbekkz.oneaviation.util.entity.Info
import com.naltynbekkz.oneaviation.util.entity.Location
import com.naltynbekkz.oneaviation.util.entity.Timestamp
import javax.persistence.*

@Entity
@Table(name = "flights")
class FlightEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    val plane: PlaneEntity? = null,
    @Embedded
    val timestamp: Timestamp? = null,

    @Embedded
    val departure: Info? = null,

    @Embedded
    val arrival: Location? = null,

    var status: FlightStatus? = null,
) {
    fun toFlight(passengerCount: Int = 0) = Flight(id!!, plane!!.toPlane(), timestamp!!, departure!!, arrival!!, status!!, passengerCount)
}