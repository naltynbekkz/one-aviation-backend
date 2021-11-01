package com.naltynbekkz.oneaviation.util

import com.naltynbekkz.oneaviation.util.entity.Role
import com.naltynbekkz.oneaviation.user.UserEntity
import com.naltynbekkz.oneaviation.user.UserRepository
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
                UserEntity(
                    id = null,
                    username = "god",
                    hashedPassword = hashUtils.hash("1920".toCharArray()),
                    role = Role.MANAGER,
                )
            )
        }
    }
}