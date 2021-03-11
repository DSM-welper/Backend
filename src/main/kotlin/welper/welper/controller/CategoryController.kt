package welper.welper.controller


import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import welper.welper.controller.response.CategoryDetailResponse
import welper.welper.controller.response.CategoryListPostResponse
import welper.welper.service.CategoryService

@RestController
@RequestMapping("/category")
class CategoryController(
        val categoryService: CategoryService,
) {
    @GetMapping("/{type}")
    fun cateGoryList(@PathVariable type: String): CategoryListPostResponse {
        return categoryService.getCategory(type)
    }

    @GetMapping("/type/{id}")
    fun categoryDetail(@PathVariable id: String): CategoryDetailResponse {
        return categoryService.detailCategory(id)
    }
}