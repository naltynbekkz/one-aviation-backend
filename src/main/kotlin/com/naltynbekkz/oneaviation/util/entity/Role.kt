package com.naltynbekkz.oneaviation.util.entity

import com.fasterxml.jackson.annotation.JsonValue

enum class Role(
    @JsonValue var value: String
) {
    GOD("God"),
    MANAGER("Manager"),
    ADMIN("Admin"),
    CUSTOMER("Customer");

    override fun toString(): String {
        return this.value
    }

    companion object {
        fun of(value: String) = values().firstOrNull { it.value == value } ?: throw Exception()
    }
}