package welper.welper.controller

import org.springframework.web.bind.annotation.*
import welper.welper.controller.request.PostRequest
import welper.welper.controller.request.SearchPostRequest
import welper.welper.controller.response.PostListResponse
import welper.welper.controller.response.PostResponse
import welper.welper.domain.Post
import welper.welper.service.PostService
import java.time.LocalDateTime
import java.time.ZoneId

@RestController
@RequestMapping("/post")
class PostController(
        val postService: PostService,
) {
    @PostMapping
    fun postCreate(@RequestHeader("Authorization") token: String, @RequestBody request: PostRequest) {
        postService.postCreate(token,
                title = request.title,
                content = request.content,
                category = request.category,
                createdAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"))
        )
    }

    @GetMapping("/search")
    fun searchPost(@RequestHeader("Authorization") token: String, @RequestBody request: SearchPostRequest)
            : PostListResponse {
        return postService.searchPost(token, request.content)
    }

    @DeleteMapping("/{id}")
    fun postDelete(
            @RequestHeader("Authorization") token: String,
            @PathVariable("id") id: Int,
    ) {
        postService.postDelete(token, id)
    }

    @GetMapping("/{id}")
    fun postRead(
            @RequestHeader("Authorization") token: String,
            @PathVariable("id") id: Int,
    ): PostResponse {
        return postService.postRead(token, id)
    }

    @GetMapping
    fun postList(@RequestHeader("Authorization") token: String): PostListResponse {
        return postService.postList(token)
    }

    @GetMapping("/{category}")
    fun postCategoryRead(
            @RequestHeader("Authorization") token: String,
            @PathVariable("category") category: String,
    ): PostListResponse {
        return postService.postCategoryRead(token, category)
    }

    @GetMapping("/mine")
    fun postMineRead(
            @RequestHeader("Authorization") token: String,
    ): PostListResponse {
        return postService.postMineRead(token)
    }
}