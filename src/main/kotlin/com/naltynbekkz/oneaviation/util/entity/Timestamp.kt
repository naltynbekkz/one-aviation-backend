package com.naltynbekkz.oneaviation.util.entity

import javax.persistence.Embeddable

@Embeddable
data class Timestamp(
    var created: Long = System.currentTimeMillis(),
    var updated: Long = System.currentTimeMillis(),
    var deleted: Long? = null
) {
    fun update() {
        updated = System.currentTimeMillis()
    }

    fun delete() {
        deleted = System.currentTimeMillis()
    }
}