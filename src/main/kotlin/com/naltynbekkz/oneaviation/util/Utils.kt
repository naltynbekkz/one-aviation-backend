package com.naltynbekkz.oneaviation.util

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import java.util.*
import java.util.regex.Pattern

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

fun <T, R> Page<T>.toPage(convert: (T) -> R): com.naltynbekkz.oneaviation.util.entity.Page<R> {
    return com.naltynbekkz.oneaviation.util.entity.Page(
        this.content.map { convert(it) },
        this.number,
        this.size,
        this.totalPages
    )
}

fun pageParams(page: Int? = 0, size: Int? = 20): PageRequest {
    return PageRequest.of(page ?: 0, size ?: 20)
}

