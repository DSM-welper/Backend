package welper.welper.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import welper.welper.domain.OpenApICategory
import welper.welper.domain.OpenApiPost

interface OpenApiCategoryRepository : JpaRepository<OpenApICategory, String> {

    fun findAllByCategoryName(categoryName: String):MutableSet<OpenApICategory>

    @Query("SELECT c FROM OpenApICategory c WHERE c.categoryName in :categoryName")
    fun findSeveralByCategory(
            categoryName: List<String>,
    ): MutableList<OpenApICategory>
}