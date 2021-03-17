package welper.welper.controller


import org.springframework.web.bind.annotation.*
import welper.welper.controller.request.CategoryRequest
import welper.welper.controller.request.SearchPostRequest
import welper.welper.controller.response.CategoryDetailResponse
import welper.welper.controller.response.CategoryListPostResponse
import welper.welper.domain.attribute.DesireArray
import welper.welper.domain.attribute.LifeArray
import welper.welper.domain.attribute.TrgterindvdlArray
import welper.welper.service.CategoryService

@RestController
@RequestMapping("/category")
class CategoryController(
        val categoryService: CategoryService,
) {
    @GetMapping
    fun cateGoryList(@RequestBody request:CategoryRequest): CategoryListPostResponse {
        return categoryService.getCategory(request.categoryName)
    }

    @GetMapping("/{id}")
    fun categoryDetail(@PathVariable id: String): CategoryDetailResponse {
        return categoryService.detailCategory(id)
    }

    @PostMapping("search")
    fun categorySearch(@RequestBody searchPostRequest: SearchPostRequest): CategoryListPostResponse {
        return categoryService.categorySearch(searchPostRequest.content)
    }
}