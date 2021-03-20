package welper.welper.controller

import org.springframework.web.bind.annotation.*
import welper.welper.controller.request.CommentsRequest
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