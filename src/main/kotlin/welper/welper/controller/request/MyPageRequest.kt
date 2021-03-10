package welper.welper.controller.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

class MyPageRequest (
        val isMarry:Boolean,

        val isWomen:Boolean,

        @get:NotBlank(message = "허용하지 않는 형식 <NULL, EMPTY, BLANK>")
        @get:Pattern(regexp = "^[a-zA-Zㄱ-ㅎ가-힣\\s]{1,3}$", message = "정규표현식 = ^[a-zA-Zㄱ-ㅎ가-힣\\s]{1,3}$")
        val age:Int,

        @get:NotBlank(message = "허용하지 않는 형식 <NULL, EMPTY, BLANK>")
        @get:Pattern(regexp = "^[a-zA-Zㄱ-ㅎ가-힣\\s]{1,12}$", message = "정규표현식 = ^[a-zA-Zㄱ-ㅎ가-힣\\s]{1,12}$")
        val name:String,
        )