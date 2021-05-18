package welper.welper.controller

import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiOperation
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import welper.welper.controller.request.MyPageRequest
import welper.welper.controller.response.LoginResponse
import welper.welper.controller.response.MyPageResponse
import welper.welper.domain.BookMark
import welper.welper.domain.User
import welper.welper.exception.UserNotFoundException
import welper.welper.repository.BookMarkRepository
import welper.welper.repository.UserRepository
import welper.welper.service.AuthService
import welper.welper.service.JwtService

@RestController
@RequestMapping("/user")
class MyPageController(
        val userRepository: UserRepository,
        val bookMarkRepository: BookMarkRepository,
        val jwtService: JwtService,
        val authService: AuthService,
) {
    @ApiImplicitParam(name = "page", value = "페이지", required = true, dataType = "int", paramType = "query")
    @ApiOperation(value = "마이페이지", notes = "query로 page만 주면됩니다.")
    @GetMapping
    fun myPage(
            @RequestHeader("Authorization") token: String,
            @PageableDefault(size = 8)
            pageable: Pageable,
    ): MyPageResponse {
        authService.validateToken(token)
        val email = jwtService.getEmail(token)
        val user: User = userRepository.findByIdOrNull(email) ?: throw UserNotFoundException(email)
        val bookMark: Page<BookMark> = bookMarkRepository.findAllByEmail(email, pageable)
        return MyPageResponse(
                email = user.email,
                marry = user.marry,
                gender = user.gender,
                age = user.age,
                name = user.name,
                disorder = user.disorder,
                bookMark = bookMark.toList().map { it.openApiPost },
                totalPage = bookMark.totalPages
        )
    }


    @ApiOperation(value = "개인정보 변경", notes = "Marry = DO,DONOT,SECRET  gender = WOMEN,MEN,SECRET  빈값은 null 혹은 아예 안넘겨주시면 됩니다.")
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping
    fun updateMyPage(
            @RequestHeader("Authorization") token: String,
            @RequestBody request: MyPageRequest,
    ) {
        authService.validateToken(token)
        val email = jwtService.getEmail(token)
        val user: User = userRepository.findByIdOrNull(email) ?: throw UserNotFoundException(email)
        user.updateMyPage(request.name, request.marry, request.gender, request.age, request.disorder)
        userRepository.save(user)
    }
}