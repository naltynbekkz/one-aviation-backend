package com.naltynbekkz.oneaviation.util

import com.naltynbekkz.oneaviation.auth.Role
import com.naltynbekkz.oneaviation.auth.User
import com.naltynbekkz.oneaviation.auth.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class InitialDataLoader @Autowired constructor(
    private val userRepository: UserRepository,
    private val hashUtils: HashUtils,
) : ApplicationRunner {

    override fun run(args: ApplicationArguments) {
        if (userRepository.findByUsername("god") == null) {
            userRepository.save(
                User(
                    id = null,
                    username = "god",
                    hashedPassword = hashUtils.hash("1920".toCharArray()),
                    role = Role.MANAGER,
                )
            )
        }
    }
}