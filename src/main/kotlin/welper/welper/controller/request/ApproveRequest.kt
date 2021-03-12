package welper.welper.controller.request

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

class ApproveRequest(
        @get:NotBlank(message = "허용하지 않는 형식 <NULL, EMPTY, BLANK>")
        val authCode: String,

        @get:NotBlank(message = "허용하지 않는 형식 <NULL, EMPTY, BLANK>")
        @get:Email
        @get:Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*.]{7,30}$", message = "정규표현식 = ^[a-zA-Z0-9|-]{7,30}$")
        val mail: String,
)