package welper.welper.controller.response

import org.springframework.data.domain.Page

data class CommentResponse(
        val list: Page<Comment>,
) {
    class Comment(
            val comment: String,
            val parents: Int,
            val depts: Int,
            val sequence: Int,
            val writer: String,
    )
}