package welper.welper.controller.response

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class CommentResponse(
        val list: List<Comment>,
        val totalOfPage:Int,
        val totalOfElements: Long,
) {
    class Comment(
            val id:Int,
            val comment: String,
            val parents: Int,
            val depts: Int,
            val sequence: Int,
            val writer: String,
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
            val createdAt: LocalDateTime,
    )
}