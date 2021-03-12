package welper.welper.controller

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/comments")
class CommentsController {
    @PostMapping("/{postId}/{commentsId}")
    fun commentsWrite(@PathVariable postId:Int, @PathVariable commentsId:Int){

    }
    @GetMapping("/{postId}")
    fun commentsRead(@PathVariable postId: Int){

    }
    @DeleteMapping("/{postId}/{commentsId}")
    fun commentsDelete(@PathVariable postId: Int, @PathVariable commentsId:Int){
    }

}