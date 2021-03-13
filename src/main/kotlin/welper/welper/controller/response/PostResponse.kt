package welper.welper.controller.response

import java.time.LocalDateTime

data class PostResponse(
        val title: String,
        val content: String,
        val category: String,
        val createdAt:LocalDateTime,
        val writer: String,
) {
}