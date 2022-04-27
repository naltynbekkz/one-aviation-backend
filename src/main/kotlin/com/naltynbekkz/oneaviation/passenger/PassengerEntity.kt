package com.naltynbekkz.oneaviation.passenger

import com.naltynbekkz.oneaviation.user.UserEntity
import com.naltynbekkz.oneaviation.util.entity.Timestamp
import javax.persistence.*

@Entity
@Table(name = "passengers")
class PassengerEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    var firstName: String? = null,
    var lastName: String? = null,
    var documentId: String? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    var user: UserEntity? = null,
    @Embedded
    var timestamp: Timestamp = Timestamp(),
) {
    fun toPassenger() = Passenger(id!!, firstName!!, lastName!!, documentId!!, timestamp)
}