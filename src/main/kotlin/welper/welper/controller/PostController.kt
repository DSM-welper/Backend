package welper.welper.controller

import org.springframework.web.bind.annotation.*
import welper.welper.controller.request.PostRequest
import welper.welper.controller.response.PostListResponse
import welper.welper.controller.response.PostResponse
import welper.welper.service.PostService
import java.time.LocalDateTime
import java.time.ZoneId

@RestController
@RequestMapping("/post")
class PostController(
        val postService: PostService,
) {
    @PostMapping
    fun postCreate(@RequestHeader("Authorization") token: String,@RequestBody request: PostRequest) {
        postService.postCreate(token,
                        title = request.title,
                        content = request.content,
                        category = request.category,
                        createdAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"))
                )
    }

    @DeleteMapping("/{id}")
    fun postDelete(@RequestHeader("Authorization") token: String,
                   @PathVariable("id") id:Int) {
        postService.postDelete(token,id)
    }

    @GetMapping("/{id}")
    fun postRead(@RequestHeader("Authorization") token: String,
                 @PathVariable("id") id:Int): PostResponse {
        return postService.postRead(token,id)
    }

    @GetMapping
    fun postList(@RequestHeader("Authorization") token: String):PostListResponse{
        return postService.postList(token)
    }
}