package com.naltynbekkz.oneaviation.flight

import com.naltynbekkz.oneaviation.plane.Plane
import com.naltynbekkz.oneaviation.plane.PlaneEntity
import com.naltynbekkz.oneaviation.util.entity.Info
import com.naltynbekkz.oneaviation.util.entity.Location
import com.naltynbekkz.oneaviation.util.entity.Timestamp
import kotlin.random.Random

data class Flight(
    val id: Long,
    val plane: Plane,
    val timestamp: Timestamp,
    val departure: Info,
    val arrival: Location,
    val status: FlightStatus,
) {

    companion object {
        fun getRandomList(): List<Flight> {
            return List(Random.nextInt(0, 7)) {
                getRandomFlight()
            }
        }

        fun getRandomFlight() = Flight(
            id = Random.nextLong(0, System.currentTimeMillis()),
            plane = getRandomPlane().toPlane(),
            timestamp = getRandomTimestamp(),
            departure = getRandomInfo(),
            arrival = getRandomLocation(),
            status = FlightStatus.CREATED,
        )

        fun getRandomPlane(): PlaneEntity {
            val names = listOf(
                "Airbus A333-300",
                "Airbus A340-300",
                "Airbus A340-500",
                "Airbus A350-900",
                "Boeing 777-200",
                "Airbus A340-600",
                "Boeing 777-300",
                "Boeing 747-400",
                "Boeing 747-8",
                "Airbus A380-800",
                "Airbus A380plus",
            )
            return PlaneEntity(
                id = null,
                name = names.random(),
                mileage = Random.nextLong(6, 10) * 10,
                capacity = Random.nextInt(6, 20),
                price = Random.nextFloat() * 100 + 50,
                timestamp = getRandomTimestamp(),
            )
        }

        fun getRandomInfo() = Info(
            time = Random.nextLong(0, 100000),
            location = getRandomLocation()
        )

        fun getRandomLocation() = Location(
            long = Random.nextFloat() * 360 - 180,
            lat = Random.nextFloat() * 360 - 180,
        )

        fun getRandomTimestamp(): Timestamp {
            val current = System.currentTimeMillis()
            return Timestamp(
                created = Random.nextLong(current, current * 101 / 100),
                updated = Random.nextLong(current, current * 101 / 100),
                deleted = null,
            )
        }

    }

//    fun getArrivalTime() {
//        return (arrival.location - departure.location) / plane.speed
//    }
}