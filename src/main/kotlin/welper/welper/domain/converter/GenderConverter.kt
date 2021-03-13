package welper.welper.domain.converter

import welper.welper.domain.attribute.Gender
import welper.welper.exception.NonExistGenderException
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class GenderConverter : AttributeConverter<Gender, String> {
    override fun convertToDatabaseColumn(gender: Gender) = gender.value

    override fun convertToEntityAttribute(gender: String) =
            Gender.values().singleOrNull { it.value == gender } ?: throw NonExistGenderException(gender)

}