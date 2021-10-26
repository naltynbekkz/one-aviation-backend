package com.naltynbekkz.oneaviation.util.entity

import com.fasterxml.jackson.annotation.JsonValue

enum class Language(
    @JsonValue var value: String
) {
    KAZAKH("kz"), RUSSIAN("ru"), ENGLISH("en");

    companion object {
        fun of(value: String) = values().firstOrNull { it.value == value }
    }
}