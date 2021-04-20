package welper.welper.controller.response

import com.fasterxml.jackson.annotation.JsonFormat
import welper.welper.domain.Comments
import java.time.LocalDateTime

data class PostResponse(
        val id: Int,
        val title: String,
        val content: String,
        val category: String,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        val createdAt: LocalDateTime,
        val writer: String,
        val isMine: Boolean,
) {
    class CommentsResponse
    (
            val sequence: Int,
            val id: Int,
            val parents: Int,
            val depts: Int,
            val comments: String,
            val postId: Int,
            val commentWriter: String,
    )
}