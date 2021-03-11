package welper.welper.controller

import org.springframework.web.bind.annotation.*
import welper.welper.service.PostService

@RestController
@RequestMapping("/post")
class PostController(
        val postService: PostService,
) {
    @PostMapping
    fun postCreate(@RequestHeader("Authorization") token: String) {
        postService.postCreate(token)
    }

    @DeleteMapping("/{id}")
    fun postDelete(@RequestHeader("Authorization") token: String,
                   @PathVariable("id") id:Int) {
        postService.postDelete(token,id)
    }

    @GetMapping("/{id}")
    fun postRead(@RequestHeader("Authorization") token: String,
                 @PathVariable("id") id:Int){
        postService.postRead(token,id)
    }

    @GetMapping
    fun postList(@RequestHeader("Authorization") token: String){
        postService.postList(token)
    }
}