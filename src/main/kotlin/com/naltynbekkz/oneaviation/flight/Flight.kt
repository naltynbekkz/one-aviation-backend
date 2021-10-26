package com.naltynbekkz.oneaviation.flight

import com.naltynbekkz.oneaviation.plane.Plane
import com.naltynbekkz.oneaviation.util.entity.Info
import com.naltynbekkz.oneaviation.util.entity.Timestamp
import javax.persistence.*

@Entity
class Flight(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    val plane: Plane? = null,
    @Embedded
    val timestamp: Timestamp? = null,

    @Embedded
    val departure: Info? = null,

    @Embedded
    val arrival: Info? = null,
) {
//    fun getArrivalTime() {
//        return (arrival.location - departure.location) / plane.speed
//    }
}