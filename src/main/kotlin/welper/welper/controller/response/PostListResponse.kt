package welper.welper.controller.response

import java.time.LocalDateTime

data class PostListResponse (
     val post:List<PostList>
){
    class PostList(
        val id:Int,
        val title:String,
        val creatAt:LocalDateTime,
        val writer :String,
    )
}