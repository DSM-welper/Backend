package welper.welper.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.TestConstructor
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.transaction.annotation.Transactional
import welper.welper.controller.request.CategoryRequest
import welper.welper.controller.request.SearchPostRequest
import welper.welper.controller.response.CategoryDetailResponse
import welper.welper.controller.response.CategoryListPostResponse
import welper.welper.domain.attribute.Category
import welper.welper.exception.handler.ExceptionResponse

@Suppress("DEPRECATION")//사용되지 않는 경고 비활성화
@SpringBootTest
@AutoConfigureMockMvc
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Transactional
internal class CategoryControllerIntegrationTest(
        private val mock: MockMvc,
        private val objectMapper: ObjectMapper,
) {
    @Test
    fun `모든 카테고리 리스트 ok`(){
        val response = objectMapper.readValue<CategoryListPostResponse>(
                mock.perform(get("/category")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .characterEncoding("UTF-8"))
                        .andExpect(MockMvcResultMatchers.status().isOk)
                        .andReturn()
                        .response
                        .contentAsString
        )
        Assertions.assertThat(response.servList).map<String> {it.servId}.containsAll(listOf("WII00000001"))
    }
    @Test
    fun `카테고리 선택 리스트 ok 1개`(){
        val list:List<Category> = listOf(Category.EDUCATION)
        val requestBody = objectMapper.writeValueAsString(
                CategoryRequest(
                        categoryName = list
                )
        )
        val response = objectMapper.readValue<CategoryListPostResponse>(
                mock.perform(get("/category/tag")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .characterEncoding("UTF-8"))
                        .andExpect(MockMvcResultMatchers.status().isOk)
                        .andReturn()
                        .response
                        .contentAsString
        )
        Assertions.assertThat(response.servList).map<String> {it.servId}.containsAll(listOf("WII00000001"))
    }
    @Test
    fun `카테고리 선택 리스트 ok 다중`(){
        val list:List<Category> = listOf(Category.EDUCATION,Category.INFANTS)
        val requestBody = objectMapper.writeValueAsString(
                CategoryRequest(
                        categoryName = list
                )
        )
        val response = objectMapper.readValue<CategoryListPostResponse>(
                mock.perform(get("/category/tag")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .characterEncoding("UTF-8"))
                        .andExpect(MockMvcResultMatchers.status().isOk)
                        .andReturn()
                        .response
                        .contentAsString
        )
        Assertions.assertThat(response.servList).map<String> {it.servId}.containsAll(listOf("WII00000001"))
    }
    @Test
    fun `카테고리 자세히 보기`(){
        val response = objectMapper.readValue<CategoryDetailResponse>(
                mock.perform(get("/category/WII00000001")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .characterEncoding("UTF-8"))
                        .andExpect(MockMvcResultMatchers.status().isOk)
                        .andReturn()
                        .response
                        .contentAsString
        )
        Assertions.assertThat(response.servId).isEqualTo("WII00000001")
    }
    @Test
    fun `카테고리 자세히 보기 없는 id`(){
        val response = objectMapper.readValue<ExceptionResponse>(
                mock.perform(get("/category/TEST")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .characterEncoding("UTF-8"))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest)
                        .andReturn()
                        .response
                        .contentAsString
        )
        Assertions.assertThat(response.code).isEqualTo("NON_EXIST_CATEGORY_DETAIL")
    }
    @Test
    fun `카테고리 제목 검색`() {
        val requestBody = objectMapper.writeValueAsString(
                SearchPostRequest(
                        content = "나야"
                )
        )
        val response = objectMapper.readValue<CategoryListPostResponse>(
                mock.perform(post("/category/search")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .characterEncoding("UTF-8"))
                        .andExpect(MockMvcResultMatchers.status().isOk)
                        .andReturn()
                        .response
                        .contentAsString
        )
        Assertions.assertThat(response.servList).map<String> { it.servId }.containsAll(listOf("WII00000001"))
    }
}