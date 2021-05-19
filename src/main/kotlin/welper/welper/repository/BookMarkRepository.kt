package welper.welper.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import welper.welper.domain.BookMark
import welper.welper.domain.OpenApiPost
import welper.welper.domain.Post

interface BookMarkRepository : JpaRepository<BookMark, Int> {
    fun existsByEmailAndOpenApiPost(email: String, post: OpenApiPost): Boolean
    fun findAllByEmail(email: String): List<BookMark>
    fun findByEmailAndOpenApiPost(email: String, post: OpenApiPost): BookMark
}