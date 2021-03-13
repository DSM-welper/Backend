package welper.welper.domain.converter

import welper.welper.domain.attribute.Marry
import welper.welper.exception.NonExistMarryException
import javax.management.Attribute
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class MarryConverter : AttributeConverter<Marry, String> {
    override fun convertToDatabaseColumn(marry: Marry) = marry.value

    override fun convertToEntityAttribute(marry: String) =
            Marry.values().singleOrNull { it.value == marry } ?: throw NonExistMarryException(marry)

}