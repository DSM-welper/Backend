package welper.welper.controller.response

import java.time.LocalDateTime

data class PostListResponse (
        val id:Int,
        val title:String,
        val creatAt:LocalDateTime,
        val name :String,
)