package welper.welper.controller

import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.bind.annotation.*
import welper.welper.controller.request.MyPageRequest
import welper.welper.controller.response.LoginResponse
import welper.welper.controller.response.MyPageResponse
import welper.welper.domain.User
import welper.welper.exception.UserNotFoundException
import welper.welper.repository.UserRepository
import welper.welper.service.JwtService

@RestController
@RequestMapping("/user")
class MyPageController(
        val userRepository: UserRepository,
        val jwtService: JwtService,
) {
    @GetMapping
    fun myPage(@RequestHeader("Authorization") token: String): MyPageResponse {
        val email = jwtService.getEmail(token)
        val user: User = userRepository.findByIdOrNull(email) ?: throw UserNotFoundException(email)
        return MyPageResponse(
                email = user.email,
                marry = user.marry,
                gender = user.gender,
                age = user.age,
                name = user.name,
        )
    }

    @PatchMapping
    fun updateMyPage(@RequestHeader("Authorization") token: String,
                     @RequestBody request:MyPageRequest) {
        val email = jwtService.getEmail(token)
        val user: User = userRepository.findByIdOrNull(email) ?: throw UserNotFoundException(email)
        user.updateMyPage(request.name, request.marry, request.gender, request.age)

        userRepository.save(user)
    }
}