package com.naltynbekkz.oneaviation.plane

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Plane(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val name: String? = null,
    val mileage: Long? = null,
    val capacity: Int? = null,
    val price: Float? = null, // price per kilometer per person in euros
)