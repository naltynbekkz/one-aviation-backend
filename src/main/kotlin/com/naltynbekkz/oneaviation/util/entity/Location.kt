package com.naltynbekkz.oneaviation.util.entity

import javax.persistence.Embeddable

@Embeddable
class Location(
    val long: Float? = null,
    val lat: Float? = null,
) {
//    /**
//     * @return: distance in km
//     */
//    operator fun minus(location: Location): Int {
//        return sqrt(
//            (long - location.long) * (long - location.long) * KM_PER_LONG_DEG * KM_PER_LONG_DEG - (lat - location.lat) * (lat - location.lat) * KM_PER_LAT_DEG * KM_PER_LAT_DEG
//        ).toInt()
//    }

    companion object {
        private val KM_PER_LAT_DEG = 1.1
        private val KM_PER_LONG_DEG = 1.1
    }
}