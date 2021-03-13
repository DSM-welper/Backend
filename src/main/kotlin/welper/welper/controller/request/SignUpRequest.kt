package welper.welper.controller.request

import welper.welper.domain.attribute.Gender
import welper.welper.domain.attribute.Marry
import javax.validation.constraints.*

data class SignUpRequest(
        @get:NotBlank(message = "허용하지 않는 형식 <NULL, EMPTY, BLANK>")
        @get:Email
        @get:Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*.]{4,30}$", message = "정규표현식 = ^[a-zA-Z0-9|-]{4,16}$")
        val email: String,

        @get:NotBlank(message = "허용하지 않는 형식 <NULL, EMPTY, BLANK>")
        @get:Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*]{4,20}$", message = "정규표현식 = ^[a-zA-Z0-9|!|@|#|$|%|^|&|*]{4,20}$")
        val password: String,

        @get:NotBlank(message = "허용하지 않는 형식 <NULL, EMPTY, BLANK>")
        @get:Pattern(regexp = "^[a-zA-Zㄱ-ㅎ가-힣]{1,12}$", message = "정규표현식 = ^[a-zA-Zㄱ-ㅎ가-힣\\s]{1,12}$")
        val name: String,

        @get:NotNull(message = "허용하지 않는 형식 <NULL, EMPTY, BLANK>")
        val age: Int,

        var marry: Marry,

        var gender: Gender,
)