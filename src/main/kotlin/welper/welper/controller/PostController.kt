package welper.welper.controller

import org.springframework.web.bind.annotation.*
import welper.welper.controller.request.PostRequest
import welper.welper.controller.request.SearchPostRequest
import welper.welper.controller.response.PostListResponse
import welper.welper.controller.response.PostResponse
import welper.welper.domain.Post
import welper.welper.service.AuthService
import welper.welper.service.PostService
import java.time.LocalDateTime
import java.time.ZoneId

@RestController
@RequestMapping("/post")
class PostController(
        val postService: PostService,
        val authService: AuthService,
) {
    @PostMapping
    fun postCreate(@RequestHeader("Authorization") token: String, @RequestBody request: PostRequest) {
        authService.validateToken(token)
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
        authService.validateToken(token)
        return postService.searchPost(token, request.content)
    }

    @DeleteMapping("/{id}")
    fun postDelete(
            @RequestHeader("Authorization") token: String,
            @PathVariable("id") id: Int,
    ) {
        authService.validateToken(token)
        postService.postDelete(token, id)
    }

    @GetMapping("/{id}")
    fun postRead(
            @RequestHeader("Authorization") token: String,
            @PathVariable("id") id: Int,
    ): PostResponse {
        authService.validateToken(token)
        return postService.postRead(token, id)
    }

    @GetMapping
    fun postList(@RequestHeader("Authorization") token: String): PostListResponse {
        authService.validateToken(token)
        return postService.postList(token)
    }

    @GetMapping("/category/{category}")
    fun postCategoryRead(
            @RequestHeader("Authorization") token: String,
            @PathVariable("category") category: String,
    ): PostListResponse {
        authService.validateToken(token)
        return postService.postCategoryRead(token, category)
    }

    @GetMapping("/mine")
    fun postMineRead(
            @RequestHeader("Authorization") token: String,
    ): PostListResponse {
        authService.validateToken(token)
        return postService.postMineRead(token)
    }
}