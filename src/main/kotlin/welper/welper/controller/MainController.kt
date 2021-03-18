package welper.welper.controller

import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.bind.annotation.*
import welper.welper.controller.response.RandomCategoryResponse
import welper.welper.domain.User
import welper.welper.exception.UserNotFoundException
import welper.welper.repository.UserRepository
import welper.welper.service.CategoryService
import welper.welper.service.JwtService

@RestController
@RequestMapping("/main")
class MainController(
        private val categoryService: CategoryService,
        ) {
    @GetMapping
    fun randomCategory(@RequestHeader("Authorization") token: String?):
            RandomCategoryResponse {
        token ?: return categoryService.randomCategory()
        return categoryService.userCategory(
                token = token
        )
    }
}