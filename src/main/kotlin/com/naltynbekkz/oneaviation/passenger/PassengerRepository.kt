package com.naltynbekkz.oneaviation.passenger

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PassengerRepository : JpaRepository<PassengerEntity, Long>