package welper.welper.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.TestConstructor
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.transaction.annotation.Transactional
import welper.welper.controller.request.MyPageRequest
import welper.welper.controller.response.MyPageResponse
import welper.welper.controller.response.PostListResponse
import welper.welper.domain.attribute.Gender
import welper.welper.domain.attribute.Marry

@Suppress("DEPRECATION")//사용되지 않는 경고 비활성화
@SpringBootTest
@AutoConfigureMockMvc
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Transactional
internal class MyPageControllerIntegrationTest(
        private val mock: MockMvc,
        private val objectMapper: ObjectMapper,
) {

    @Test
    fun `마이페이지 확인 ok`() {
        val response = objectMapper.readValue<MyPageResponse>(
                mock.perform(MockMvcRequestBuilders.get("/user")
                        .header("Authorization", "this-is-test-token")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .characterEncoding("UTF-8"))
                        .andExpect(MockMvcResultMatchers.status().isOk)
                        .andReturn()
                        .response
                        .contentAsString
        )
        assertThat(response.email).isNotBlank
        assertThat(response.marry)
        assertThat(response.gender)
        assertThat(response.age)
        assertThat(response.name).isNotBlank
        assertThat(response.disorder)
    }

    @Test
    fun `마이페이지 업데이트 ok`() {
        val requestBody = objectMapper.writeValueAsString(
                MyPageRequest(
                        marry = Marry.DO,
                        gender = Gender.WOMEN,
                        disorder = true,
                        age = 13,
                        name = "네임",
                )
        )
        mock.perform(MockMvcRequestBuilders.patch("/user")
                .header("Authorization", "this-is-test-token")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()
                .response
                .contentAsString
    }
    @Test
    fun `마이페이지 null 값 체크`() {
        val requestBody = objectMapper.writeValueAsString(
                MyPageRequest(
                        marry = Marry.DO,
                        gender = Gender.WOMEN,
                        disorder = true,
                        age = 13,
                )
        )
        mock.perform(MockMvcRequestBuilders.patch("/user")
                .header("Authorization", "this-is-test-token")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()
                .response
                .contentAsString
    }
//    @Test
//    fun `마이페이지 파라미터 입력 x`(){
//        var response:
//    }
}
