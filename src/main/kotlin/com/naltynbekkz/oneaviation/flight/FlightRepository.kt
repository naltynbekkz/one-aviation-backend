package com.naltynbekkz.oneaviation.flight

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FlightRepository : JpaRepository<FlightEntity, Long>