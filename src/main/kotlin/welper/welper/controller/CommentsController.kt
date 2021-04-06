package welper.welper.controller

import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*
import welper.welper.controller.request.CommentsRequest
import welper.welper.controller.response.CommentResponse
import welper.welper.service.AuthService
import welper.welper.service.CommentsService

@RestController
@RequestMapping("/comments")
class CommentsController(
        val commentsService: CommentsService,
        val authService: AuthService,
) {
    @PostMapping("/{postId}/{commentsId}")
    fun commentsParents(
            @RequestHeader("Authorization") token: String,
            @PathVariable postId: Int,
            @PathVariable commentsId: Int,
            @RequestBody commentsRequest: CommentsRequest,
    ) {
        authService.validateToken(token)
        commentsService.commentsParents(postId, commentsId, commentsRequest.contents, token)
    }

    @PostMapping("/{postId}")
    fun commentsWrite(
            @RequestHeader("Authorization") token: String,
            @PathVariable postId: Int, @RequestBody commentsRequest: CommentsRequest,
    ) {
        authService.validateToken(token)
        commentsService.commentsWrite(token, postId, commentsRequest.contents)
    }
    @ApiImplicitParam(name = "page", value = "페이지", required = true, dataType = "int", paramType = "query")
    @ApiOperation(value="댓글 리스트",notes = "query로 page만 주면됩니다.")
    @GetMapping("/{postId}")
    fun commentListRead(
            @RequestHeader("Authorization") token: String,
            @PathVariable("postId") postId: Int,
            @PageableDefault(size=6, sort= ["sequence"])
            pageable: Pageable,
    ): CommentResponse {
        authService.validateToken(token)
        return commentsService.commentListRead(postId,pageable)
    }

    @DeleteMapping("/{postId}/{commentsId}")
    fun commentsDelete(
            @PathVariable postId: Int,
            @PathVariable commentsId: Int,
            @RequestHeader("Authorization") token: String,
    ) {
        authService.validateToken(token)
        commentsService.commentsDelete(token, postId, commentsId)
    }

}