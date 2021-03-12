package welper.welper.controller

import org.springframework.web.bind.annotation.*
import welper.welper.controller.request.CommentsRequest
import welper.welper.service.CommentsService

@RestController
@RequestMapping("/comments")
class CommentsController(
        val commentsService: CommentsService,
) {
    @PostMapping("/{postId}/{commentsId}")
    fun commentsParents(
            @RequestHeader("Authorization") token: String,
            @PathVariable postId: Int,
            @PathVariable commentsId: Int,
            @RequestBody commentsRequest: CommentsRequest,
    ) {
        commentsService.commentsParents(postId, commentsId, commentsRequest.contents, token)
    }

    @PostMapping("/{postId}")
    fun commentsWrite(
            @RequestHeader("Authorization") token: String,
            @PathVariable postId: Int, @RequestBody commentsRequest: CommentsRequest,
    ) {
        commentsService.commentsWrite(token,postId,commentsRequest.contents)
    }

    @GetMapping("/{postId}")
    fun commentsRead(
            @RequestHeader("Authorization") token: String,
            @PathVariable postId: Int,
    ) {

    }

    @DeleteMapping("/{postId}/{commentsId}")
    fun commentsDelete(
            @PathVariable postId: Int,
            @PathVariable commentsId: Int,
            @RequestHeader("refreshToken") token: String,
    ) {
    }

}