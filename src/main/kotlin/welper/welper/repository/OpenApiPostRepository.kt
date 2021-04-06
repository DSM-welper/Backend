package welper.welper.repository

import org.springframework.data.jpa.repository.JpaRepository
import welper.welper.domain.OpenApiPost

interface OpenApiPostRepository : JpaRepository<OpenApiPost, String> {
}