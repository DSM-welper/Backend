package welper.welper.repository

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.jpa.repository.Query
import org.springframework.test.context.TestConstructor
import welper.welper.domain.OpenApICategory
import welper.welper.domain.attribute.Category

@DataJpaTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
internal class OpenApiCategoryRepositoryTest(
        val openApiCategoryRepository: OpenApiCategoryRepository,
) {
    @Test
    fun `categoryname으로 검색 OK`() {
        val categoryName = openApiCategoryRepository.findAllByCategoryName("여성")
        Assertions.assertThat(categoryName).map<Int> { it.id }.containsAll(listOf(7))
    }

    @Test
    fun `없는 categoryname으로 검색`() {
        assertThat(
                openApiCategoryRepository.findAllByCategoryName("내가누구야")
        ).isEmpty()
    }

    @Test
    fun `여러 카테고리이름으로 검색`() {
        val list: List<String> = listOf(Category.INFANTS.value)
        val categoryName = openApiCategoryRepository.findSeveralByCategory(list)
        assertThat(categoryName).map<Int> { it.id }.containsAll(listOf(4))
    }
}