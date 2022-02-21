package com.naltynbekkz.oneaviation.pilot

import com.naltynbekkz.oneaviation.util.entity.Timestamp
import javax.persistence.*

@Entity
@Table(name = "pilots")
class PilotEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var firstName: String? = null,
    var lastName: String? = null,
    var profession: String? = null,

    @Embedded
    var timestamp: Timestamp = Timestamp(),
) {
    fun toPilot() = Pilot(id!!, firstName!!, lastName!!, profession!!, timestamp)
}