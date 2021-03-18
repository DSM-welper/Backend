package welper.welper.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.TestConstructor
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.transaction.annotation.Transactional
import welper.welper.controller.request.LoginRequest
import welper.welper.controller.response.LoginResponse
import org.assertj.core.api.Assertions.assertThat

@Suppress("DEPRECATION")//사용되지 않는 경고 비활성화
@SpringBootTest
@AutoConfigureMockMvc
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Transactional
internal class AuthControllerIntegrationTest(
        private val mock: MockMvc,
        private val objectMapper: ObjectMapper,
) {
    @Test
    fun `로그인 OK`() {
        val requestBody = objectMapper.writeValueAsString(
                LoginRequest(
                        email = "test@email.com",
                        password = "testpassword",
                )
        )
        val response = objectMapper.readValue<LoginResponse>(
                mock.perform(post("/auth")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .characterEncoding("UTF-8"))
                        .andExpect(status().isOk)
                        .andReturn()
                        .response
                        .contentAsString
        )

        assertThat(response.accessToken).isNotBlank
        assertThat(response.refreshToken).isNotBlank
    }
}