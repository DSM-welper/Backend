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
import welper.welper.controller.request.SearchPostRequest
import welper.welper.controller.response.CategoryListPostResponse
import welper.welper.controller.response.RandomCategoryResponse

@Suppress("DEPRECATION")//사용되지 않는 경고 비활성화
@SpringBootTest
@AutoConfigureMockMvc
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Transactional
class MainControllerIntegrationTest
(
        val mock: MockMvc,
        val objectMapper: ObjectMapper,

        ) {
    @Test
    fun `메인페이지 토큰 비어서 줬을때 ok`(){
        val response = objectMapper.readValue<RandomCategoryResponse>(
                mock.perform(MockMvcRequestBuilders.get("/main")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .characterEncoding("UTF-8"))
                        .andExpect(MockMvcResultMatchers.status().isOk)
                        .andReturn()
                        .response
                        .contentAsString
        )
        Assertions.assertThat(response.ageList.list).map<String> { it.servId }.containsAll(listOf("WII00000001"))
    }
    @Test
    fun `메인페이지 넣어서 줬을때 ok`(){
        val response = objectMapper.readValue<RandomCategoryResponse>(
                mock.perform(MockMvcRequestBuilders.get("/main")
                        .header("Authorization", "this-is-test-token")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .characterEncoding("UTF-8"))
                        .andExpect(MockMvcResultMatchers.status().isOk)
                        .andReturn()
                        .response
                        .contentAsString
        )
        Assertions.assertThat(response.genderList.list).map<String> { it.servId }.containsAll(listOf("WII00000001"))
    }
}