package welper.welper.controller


import org.springframework.web.bind.annotation.*
import welper.welper.controller.request.CategoryRequest
import welper.welper.controller.request.SearchPostRequest
import welper.welper.controller.request.PageRequest
import welper.welper.controller.request.SearchCategoryRequest
import welper.welper.controller.response.CategoryDetailResponse
import welper.welper.controller.response.CategoryListPostResponse
import welper.welper.service.AuthService
import welper.welper.service.CategoryService
import javax.validation.Valid

@RestController
@RequestMapping("/category")
class CategoryController(
        val categoryService: CategoryService,
        val authService: AuthService,
        ) {
    @GetMapping("/tag")
    fun showCategoryTagList(@RequestHeader("Authorization") token: String?, request: CategoryRequest): CategoryListPostResponse {
        return categoryService.showCategoryTagList(
                request.categoryName, request.numOfPage,token)
    }

    @GetMapping
    fun showCategoryList(@RequestHeader("Authorization") token: String, request: PageRequest): CategoryListPostResponse {
        return categoryService.showCategoryList(request.numOfPage,token)
    }

    @GetMapping("/detail/{id}")
    fun showDetailCategory(@PathVariable id: String): CategoryDetailResponse {
        return categoryService.showDetailCategory(id)
    }

    @GetMapping("/search")
    fun showSearchCategory(@RequestHeader("Authorization") token: String ,request: SearchCategoryRequest): CategoryListPostResponse {
        return categoryService.showSearchCategory(request.content, request.numOfPage,token)
    }

    @PostMapping("/bookMark/{servId}")
    fun bookMarkCategory(@RequestHeader("Authorization") token: String, @PathVariable servId: String) {
        authService.validateToken(token)
        return categoryService.bookMarkCategory(token,servId)
    }
}