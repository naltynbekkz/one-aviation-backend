package com.naltynbekkz.oneaviation.plane

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PlaneRepository : JpaRepository<Plane, Long>