package com.naltynbekkz.oneaviation.util

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import java.util.*
import java.util.regex.Pattern

fun isValidEmailAddress(email: String): Boolean {

//        https://stackoverflow.com/questions/201323/how-to-validate-an-email-address-using-a-regular-expression
    val regex =
        """(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)])"""
    val p = Pattern.compile(regex)
    val m = p.matcher(email)
    return m.matches()

}

fun generateString(): String {
    return UUID.randomUUID().toString()
}

//    Minimum eight characters, at least one letter and one number
fun isValidPassword(password: String): Boolean {
    val regex = """^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}"""
    val p = Pattern.compile(regex)
    val m = p.matcher(password)
    return m.matches()
}

fun isValidPhoneNumber(phone: String): Boolean {
    if (phone.length != 10 || phone[0] != '7') {
        return false
    }
    phone.forEach {
        if (!it.isDigit()) {
            return false
        }
    }
    return true
}

fun <T> Page<T>.toPage(): com.naltynbekkz.oneaviation.util.entity.Page<T> {
    return com.naltynbekkz.oneaviation.util.entity.Page(this.content, this.number, this.size, this.totalPages)
}

fun pageParams(page: Int? = 0, size: Int? = 20): PageRequest {
    return PageRequest.of(page ?: 0, size ?: 20)
}

fun getFileExtension(fileName: String): String {
    val lastIndexOf = fileName.lastIndexOf(".")
    return if (lastIndexOf == -1) fileName else fileName.substring(lastIndexOf)
}