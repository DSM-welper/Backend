package welper.welper.controller.request

import welper.welper.domain.attribute.Gender
import welper.welper.domain.attribute.Marry
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

class MyPageRequest(
        val marry: Marry?=null,

        val gender: Gender?=null,

        val disorder: Boolean?=null,

        val age: Int?=null,

        val name: String?=null,
)