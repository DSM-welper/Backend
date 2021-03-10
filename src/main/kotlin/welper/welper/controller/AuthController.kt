package welper.welper.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import welper.welper.controller.request.SignUpRequest
import welper.welper.service.AuthService
import javax.validation.Valid


@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService,
) {
    @PostMapping("/signUp")
    fun signUp(@RequestBody @Valid request: SignUpRequest) =
        authService.signUp(
            email = request.email,
            password = request.password,
            name=request.name,
        )
//    @PostMapping("login")
//    fun login(@RequestBody @Valid) =
//            authService.login
//
}