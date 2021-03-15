package welper.welper.controller.request

import welper.welper.domain.attribute.DesireArray
import welper.welper.domain.attribute.LifeArray
import welper.welper.domain.attribute.TrgterindvdlArray

data class CategoryRequest(
        val lifeArray: LifeArray,
        val desireArray: DesireArray,
        val trgterindvdlArray: TrgterindvdlArray,
)