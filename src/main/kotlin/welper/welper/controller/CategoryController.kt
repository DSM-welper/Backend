package welper.welper.controller


import org.springframework.web.bind.annotation.*
import welper.welper.controller.request.CategoryRequest
import welper.welper.controller.request.SearchPostRequest
import welper.welper.controller.request.PageRequest
import welper.welper.controller.response.CategoryDetailResponse
import welper.welper.controller.response.CategoryListPostResponse
import welper.welper.service.CategoryService

@RestController
@RequestMapping("/category")
class CategoryController(
        val categoryService: CategoryService,
) {
    @GetMapping("/tag")
    fun showCategoryTagList(@RequestBody request: CategoryRequest): CategoryListPostResponse {
        return categoryService.showCategoryTagList(
                request.categoryName,request.numOfPage)
    }

    @GetMapping
    fun showCategoryList(@RequestBody request: PageRequest): CategoryListPostResponse {
        return categoryService.showCategoryList(request.numOfPage)
    }

    @GetMapping("/detail/{id}")
    fun showDetailCategory(@PathVariable id: String): CategoryDetailResponse {
            return categoryService.showDetailCategory(id)
    }

    @GetMapping("/search")
    fun showSearchCategory(@RequestBody searchPostRequest: SearchPostRequest): CategoryListPostResponse {
        return categoryService.showSearchCategory(searchPostRequest.content,searchPostRequest.numOfPage)
    }
}