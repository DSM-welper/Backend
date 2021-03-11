package welper.welper.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import welper.welper.service.PostService

@RestController
@RequestMapping("/post")
class PostController(
        val postService: PostService,
) {
    @PostMapping()

}