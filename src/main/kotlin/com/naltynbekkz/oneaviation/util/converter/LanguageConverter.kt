package com.naltynbekkz.oneaviation.util.converter

import com.naltynbekkz.oneaviation.util.entity.Language
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter(autoApply = true)
class LanguageConverter : AttributeConverter<Language, String> {
    override fun convertToDatabaseColumn(obj: Language?) = obj?.value

    override fun convertToEntityAttribute(value: String?) = value?.let { Language.of(it) }
}

class StringToLanguageConverter : org.springframework.core.convert.converter.Converter<String, Language> {
    override fun convert(value: String): Language {
        return Language.of(value)!!
    }
}
