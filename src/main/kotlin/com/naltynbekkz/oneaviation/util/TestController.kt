package com.naltynbekkz.oneaviation.util

import com.naltynbekkz.oneaviation.auth.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("test")
class TestController(
    private val userRepository: UserRepository
) {


    @GetMapping
    fun request(
        @RequestParam id: Long,
        @RequestParam getFull: Boolean,
        response: HttpServletResponse,
    ) {
        response.status = HttpStatus.NO_CONTENT.value()
    }

}