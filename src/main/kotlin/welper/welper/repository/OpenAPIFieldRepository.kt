package welper.welper.repository

import org.springframework.data.jpa.repository.JpaRepository
import welper.welper.domain.OpenAPIField

interface OpenAPIFieldRepository : JpaRepository<OpenAPIField, String> {
    fun existsByApiIdAndFieldContent(apiId: String, fieldContent: String):Boolean?
}