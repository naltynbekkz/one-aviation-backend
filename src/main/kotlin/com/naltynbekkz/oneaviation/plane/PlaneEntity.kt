package com.naltynbekkz.oneaviation.plane

import com.naltynbekkz.oneaviation.user.UserEntity
import com.naltynbekkz.oneaviation.util.entity.Timestamp
import javax.persistence.*
import kotlin.random.Random

@Entity
@Table(name = "planes")
class PlaneEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var name: String? = null,
    var mileage: Long? = null,
    var capacity: Int? = null,
    var price: Float? = null, // price per kilometer per person in euros,

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    var author: UserEntity? = null,
    @Embedded
    var timestamp: Timestamp = Timestamp(),
) {
    fun toPlane() = Plane(id ?: Random.nextLong(0, 100000), name!!, mileage!!, capacity!!, price!!, timestamp)
}