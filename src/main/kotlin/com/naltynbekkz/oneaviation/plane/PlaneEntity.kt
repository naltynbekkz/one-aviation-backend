package com.naltynbekkz.oneaviation.plane

import javax.persistence.*

@Entity
@Table(name = "planes")
class PlaneEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val name: String? = null,
    val mileage: Long? = null,
    val capacity: Int? = null,
    val price: Float? = null, // price per kilometer per person in euros
)