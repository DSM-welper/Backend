package welper.welper.controller.response

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class PostListResponse (
     val totalOfPage:Int,
     val totalOfElements:Long,
     val post:List<PostList>
){
    class PostList(
        val id:Int,
        val title:String,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        val creatAt:LocalDateTime,
        val category:String,
        val writer :String,
    )
}