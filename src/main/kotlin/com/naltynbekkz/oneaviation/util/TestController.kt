package com.naltynbekkz.oneaviation.util

import com.naltynbekkz.oneaviation.util.entity.Location
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test")
class TestController {

    @GetMapping
    fun request(): List<Location> {
        return listOf(Location(1f, 2f))
    }

}