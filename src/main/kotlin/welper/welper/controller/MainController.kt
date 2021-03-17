package welper.welper.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import welper.welper.controller.response.RandomCategoryResponse
import welper.welper.service.CategoryService

@RestController
@RequestMapping("/main")
class MainController(
        private val categoryService: CategoryService,
) {
    @GetMapping
    fun randomCategory(): RandomCategoryResponse {
        return categoryService.randomCategory()
    }
}