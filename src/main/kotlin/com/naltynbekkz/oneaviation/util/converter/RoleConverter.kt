package com.naltynbekkz.oneaviation.util.converter

import com.naltynbekkz.oneaviation.auth.Role
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter(autoApply = true)
class RoleConverter : AttributeConverter<Role, String> {
    override fun convertToDatabaseColumn(obj: Role?) = obj?.value

    override fun convertToEntityAttribute(value: String?) = value?.let { Role.of(it) }
}