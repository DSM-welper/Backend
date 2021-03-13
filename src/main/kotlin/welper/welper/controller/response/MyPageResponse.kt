package welper.welper.controller.response

import welper.welper.domain.attribute.Gender
import welper.welper.domain.attribute.Marry

data class MyPageResponse (
        val email:String,
        val marry:Marry,
        val gender: Gender,
        val age:Int,
        val name:String,
        )