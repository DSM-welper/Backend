package welper.welper.controller.response

import welper.welper.domain.Comments
import java.time.LocalDateTime

data class PostResponse(
        val title: String,
        val content: String,
        val category: String,
        val createdAt: LocalDateTime,
        val writer: String,
        val comment: List<CommentsResponse>,
) {
    class CommentsResponse(
            val id: Int,
            val parents: Int,
            val depts: Int,
            val comments: String,
            val postId: Int,
            val writer:String,
    )
}