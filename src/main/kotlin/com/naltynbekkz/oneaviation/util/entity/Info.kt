package com.naltynbekkz.oneaviation.util.entity

import javax.persistence.Embeddable
import javax.persistence.Embedded

@Embeddable
class Info(
    val time: Long? = null,
    @Embedded
    val location: Location? = null,
)