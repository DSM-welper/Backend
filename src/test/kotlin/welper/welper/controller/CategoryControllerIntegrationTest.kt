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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.transaction.annotation.Transactional
import welper.welper.controller.response.CategoryListPostResponse
import welper.welper.controller.response.MyPageResponse

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
    fun `카테고리 리스트 ok`(){
        val response = objectMapper.readValue<CategoryListPostResponse>(
                mock.perform(MockMvcRequestBuilders.get("/category")
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
}