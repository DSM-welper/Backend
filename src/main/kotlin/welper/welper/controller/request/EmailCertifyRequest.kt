package welper.welper.controller.request

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class EmailCertifyRequest(
        @get:NotBlank(message = "허용하지 않는 형식 <NULL, EMPTY, BLANK>")
        @get:Email
        @get:Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*.]{7,30}$", message = "정규표현식 = ^[a-zA-Z0-9|-]{7,30}$")
        val email: String,
)