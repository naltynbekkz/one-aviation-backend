package com.naltynbekkz.oneaviation.auth

import com.fasterxml.jackson.annotation.JsonValue

enum class Role(
    @JsonValue var value: String
) {
    GOD("god"),
    MANAGER("manager"),
    ADMIN("admin"),
    CUSTOMER("customer");

    override fun toString(): String {
        return this.value
    }

    companion object {
        fun of(value: String) = values().firstOrNull { it.value == value } ?: throw Exception()
    }
}