package welper.welper.controller.request

import welper.welper.domain.attribute.Category

data class CategoryRequest(
        val numOfPage:Int,
        val categoryName:List<Category>
)