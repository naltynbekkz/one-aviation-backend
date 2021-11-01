package com.naltynbekkz.oneaviation.flight

import com.naltynbekkz.oneaviation.plane.PlaneEntity
import com.naltynbekkz.oneaviation.util.entity.Info
import com.naltynbekkz.oneaviation.util.entity.Timestamp
import javax.persistence.*

@Entity
@Table(name = "flights")
class FlightEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    val plane: PlaneEntity? = null,
    @Embedded
    val timestamp: Timestamp? = null,

    @Embedded
    val departure: Info? = null,

    @Embedded
    val arrival: Info? = null,
) {
    fun toFlight() = Flight(id!!, plane!!.toPlane(), timestamp!!, departure!!, arrival!!)
}