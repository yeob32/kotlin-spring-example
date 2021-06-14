package com.example.demo.global.jpa

import javax.persistence.AttributeConverter
import javax.persistence.Converter

// 모든 Boolean 타입에 자동 설정
@Converter(autoApply = true)
class BooleanToYNConverter : AttributeConverter<Boolean, String> {
    override fun convertToDatabaseColumn(attribute: Boolean?): String {
        return if (attribute != null && attribute) "Y" else "N"
    }

    override fun convertToEntityAttribute(dbData: String?): Boolean {
        return dbData == "Y"
    }
}