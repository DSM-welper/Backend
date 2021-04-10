package welper.welper.controller

import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiOperation
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*
import welper.welper.controller.request.PageRequest
import welper.welper.controller.request.PostRequest
import welper.welper.controller.request.SearchPostRequest
import welper.welper.controller.response.CommentResponse
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

    @ApiImplicitParam(name = "page", value = "페이지", required = true, dataType = "int", paramType = "query")
    @ApiOperation(value = "제목 검색 포스트 리스트", notes = "query로 page만 주면됩니다.")
    @GetMapping("/search")
    fun searchPost(
            @RequestHeader("Authorization") token: String, request: SearchPostRequest,
            @PageableDefault(size = 5, sort = ["id"])
            pageable: Pageable,
    )
            : PostListResponse {
        authService.validateToken(token)
        return postService.searchPost(token, request.content, pageable)
    }

    @DeleteMapping("/{id}")
    fun postDelete(
            @RequestHeader("Authorization") token: String,
            @PathVariable("id") id: Int,
    ) {
        authService.validateToken(token)
        postService.postDelete(token, id)
    }


    @GetMapping("/detail/{id}")
    fun postDetailRead(
            @RequestHeader("Authorization") token: String,
            @PathVariable("id") id: Int,
    ): PostResponse {
        authService.validateToken(token)
        return postService.postDetailRead(token, id)
    }

    @GetMapping
    @ApiImplicitParam(name = "page", value = "페이지", required = true, dataType = "int", paramType = "query")
    @ApiOperation(value = "포스트 리스트", notes = "query로 page만 주면됩니다.")
    fun postList(
            @RequestHeader("Authorization") token: String,
            @PageableDefault(size = 5, sort = ["id"])
            pageable: Pageable,
    ): PostListResponse {
        authService.validateToken(token)
        return postService.postList(token, pageable)
    }

    @GetMapping("/category/{category}")
    @ApiImplicitParam(name = "page", value = "페이지", required = true, dataType = "int", paramType = "query")
    @ApiOperation(value = "카테고리 검색 포스트 리스트", notes = "query로 page만 주면됩니다.")
    fun postCategoryRead(
            @RequestHeader("Authorization") token: String,
            @PathVariable("category") category: String,
            @PageableDefault(size = 5, sort = ["id"])
            pageable: Pageable,
    ): PostListResponse {
        authService.validateToken(token)
        return postService.postCategoryRead(token, category, pageable)
    }

    @GetMapping("/mine")
    @ApiImplicitParam(name = "page", value = "페이지", required = true, dataType = "int", paramType = "query")
    @ApiOperation(value = "자신의 포스트 리스트", notes = "query로 page만 주면됩니다.")
    fun postMineRead(
            @RequestHeader("Authorization") token: String,
            @PageableDefault(size = 5, sort = ["id"])
            pageable: Pageable,
    ): PostListResponse {
        authService.validateToken(token)
        return postService.postMineRead(token, pageable)
    }

    @GetMapping("/test")
    fun test(
            @RequestHeader("Authorization") token: String,
            request: PageRequest
    ):PostListResponse{
        authService.validateToken(token)
        return postService.test(request.numOfPage)
    }
}