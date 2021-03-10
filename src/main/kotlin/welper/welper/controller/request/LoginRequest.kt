package welper.welper.controller.request

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

class LoginRequest(
        @get:NotBlank(message = "허용하지 않는 형식 <NULL, EMPTY, BLANK>")
        @get:Email
        @get:Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*.]{7,30}$", message = "정규표현식 = ^[a-zA-Z0-9|-]{7,30}$")
        val email: String,

        @get:NotBlank(message = "허용하지 않는 형식 <NULL, EMPTY, BLANK>")
        @get:Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*]{4,20}$", message = "정규표현식 = ^[a-zA-Z0-9|!|@|#|$|%|^|&|*]{4,20}$")
        val password: String,
)
