package welper.welper.repository

import org.springframework.data.jpa.repository.JpaRepository
import welper.welper.domain.OpenAPI

interface OpenAPIRepository : JpaRepository<OpenAPI, String> {
}