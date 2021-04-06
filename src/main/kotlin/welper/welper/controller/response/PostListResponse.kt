package welper.welper.controller.response

import java.time.LocalDateTime

data class PostListResponse (
     val totalPage:Int,
     val post:List<PostList>
){
    class PostList(
        val id:Int,
        val title:String,
        val creatAt:LocalDateTime,
        val category:String,
        val writer :String,
    )
}