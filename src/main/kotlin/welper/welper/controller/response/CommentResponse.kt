package welper.welper.controller.response

data class CommentResponse(
        val list: List<Comment>,
        val totalOfPage:Int,
        val totalOfElements: Long,
) {
    class Comment(
            val comment: String,
            val parents: Int,
            val depts: Int,
            val sequence: Int,
            val writer: String,
    )
}