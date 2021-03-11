package welper.welper.controller

import org.springframework.web.bind.annotation.*
import welper.welper.service.PostService

@RestController
@RequestMapping("/post")
class PostController(
        val postService: PostService,
) {
    @PostMapping
    fun postCreate() {

    }

    @DeleteMapping
    fun postDelete() {

    }

    @GetMapping
    fun postRead() {

    }

    @GetMapping
    fun postList(){
            
    }
}