package welper.welper.controller


import org.springframework.web.bind.annotation.*
import welper.welper.controller.request.CategoryRequest
import welper.welper.controller.request.SearchPostRequest
import welper.welper.controller.request.PageRequest
import welper.welper.controller.request.SearchCategoryRequest
import welper.welper.controller.response.CategoryDetailResponse
import welper.welper.controller.response.CategoryListPostResponse
import welper.welper.service.CategoryService
import javax.validation.Valid

@RestController
@RequestMapping("/category")
class CategoryController(
        val categoryService: CategoryService,
) {
    @GetMapping("/tag")
    fun showCategoryTagList(request: CategoryRequest): CategoryListPostResponse {
        return categoryService.showCategoryTagList(
                request.categoryName,request.numOfPage)
    }

    @GetMapping
    fun showCategoryList(request: PageRequest): CategoryListPostResponse {
        return categoryService.showCategoryList(request.numOfPage)
    }

    @GetMapping("/detail/{id}")
    fun showDetailCategory(@PathVariable id: String): CategoryDetailResponse {
            return categoryService.showDetailCategory(id)
    }

    @GetMapping("/search")
    fun showSearchCategory(request: SearchCategoryRequest): CategoryListPostResponse {
        return categoryService.showSearchCategory(request.content,request.numOfPage)
    }
}