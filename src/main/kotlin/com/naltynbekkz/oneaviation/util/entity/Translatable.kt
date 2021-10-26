package com.naltynbekkz.oneaviation.util.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.Embeddable
import javax.persistence.Transient


@Embeddable
class Translatable(
    var kz: String? = null,
    var ru: String? = null,
    var en: String? = null,
) {

    constructor(translatable: Translatable?) : this(
        kz = translatable?.kz,
        ru = translatable?.ru,
        en = translatable?.en
    ) {
        translatable?.let { assert(isValid()) }
    }

    fun update(translatable: Translatable?) {
        kz = translatable?.kz
        ru = translatable?.ru
        en = translatable?.en
        translatable?.let { assert(isValid()) }
    }

    @JsonIgnore
    @Transient
    fun isValid() = kz != null || ru != null || en != null
}