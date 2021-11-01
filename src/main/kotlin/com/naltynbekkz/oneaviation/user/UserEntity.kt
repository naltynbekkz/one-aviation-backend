package com.naltynbekkz.oneaviation.user

import com.naltynbekkz.oneaviation.util.entity.Role
import com.naltynbekkz.oneaviation.util.entity.Timestamp
import javax.persistence.*

@Entity
@Table(name = "users")
class UserEntity constructor(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(unique = true)
    var username: String? = null,

    var firstName: String? = null,
    var lastName: String? = null,

    var hashedPassword: String? = null,

    @Column(nullable = false)
    var role: Role? = null,

    @Embedded
    var timestamp: Timestamp = Timestamp(),
) {
    fun toUser() = User(id!!, username!!, firstName!!, lastName!!, timestamp)
}