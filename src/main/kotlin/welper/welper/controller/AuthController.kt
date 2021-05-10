package welper.welper.controller

import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*
import welper.welper.controller.request.LoginRequest
import welper.welper.controller.request.PasswordRequest
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
    @ApiOperation(value = "회원가입", notes = "Marry = DO,DONOT,SECRET  gender = WOMEN,MEN,SECRET")
    @PostMapping("/signup")
    fun signup(@RequestBody @Valid request: SignUpRequest) =
            authService.signup(
                    email = request.email,
                    password = request.password,
                    name = request.name,
                    marry = request.marry,
                    gender = request.gender,
                    age = request.age,
                    disorder = request.disorder,
            )

    @PostMapping
    fun signin(@RequestBody @Valid request: LoginRequest): LoginResponse =
            authService.signin(
                    email = request.email,
                    password = request.password,
            )

    @DeleteMapping
    fun withdrawalUser(
            request: PasswordRequest,
            @RequestHeader("Authorization") token: String,
    ) {
        authService.validateToken(token)
        authService.withdrawalUser(request.password, token)
    }

    @PatchMapping
    fun recreateToken(@RequestHeader("refreshToken") token: String): AccessTokenResponse =
            AccessTokenResponse(authService.recreateAccessToken(token))

    @PostMapping("/token")
    fun validateToken(@RequestHeader("Authorization") token: String) =
            authService.validateToken(token)
}