package welper.welper.controller

import org.springframework.web.bind.annotation.*
import welper.welper.controller.request.LoginRequest
import welper.welper.controller.request.SignUpRequest
import welper.welper.controller.response.AccessTokenResponse
import welper.welper.controller.response.LoginResponse
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
                    name = request.name,
                    marry = request.marry,
                    gender = request.gender,
                    age = request.age,
                    disorder = request.disorder,
            )

    @PostMapping
    fun login(@RequestBody @Valid request: LoginRequest): LoginResponse =
            authService.login(
                    email = request.email,
                    password = request.password,
            )

    @PatchMapping
    fun recreateToken(@RequestHeader("refreshToken") token: String): AccessTokenResponse =
            AccessTokenResponse(authService.recreateAccessToken(token))
    @PostMapping("/token")
    fun validateToken(@RequestHeader("Authorization") token: String) =
            authService.validateToken(token)

}