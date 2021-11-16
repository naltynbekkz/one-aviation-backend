package com.naltynbekkz.oneaviation.book

import com.naltynbekkz.oneaviation.passenger.PassengerEntity
import com.naltynbekkz.oneaviation.user.UserEntity
import com.naltynbekkz.oneaviation.util.entity.Info
import com.naltynbekkz.oneaviation.util.entity.Location
import com.naltynbekkz.oneaviation.util.entity.Timestamp
import javax.persistence.*

@Entity
@Table(name = "books")
class BookEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val passengerCount: Int? = null,
    @Embedded
    val departure: Info? = null,
    @Embedded
    val arrival: Location? = null,

    @ManyToMany
    var passengers: List<PassengerEntity>? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    var user: UserEntity? = null,

    @Embedded
    val timestamp: Timestamp? = Timestamp(),
) {
    fun toBook() = Book(
        id = id!!,
        passengerCount = passengerCount!!,
        departure = departure!!,
        arrival = arrival!!,
        passengers = passengers!!.map { it.toPassenger() },
        timestamp = timestamp!!
    )
}