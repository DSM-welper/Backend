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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import welper.welper.controller.response.AccessTokenResponse
import welper.welper.exception.handler.ExceptionResponse

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

    @Test
    fun `로그인 - 이메일과 일치하는 계정 없음 User NotFound Exception`() {
        val requestBody = objectMapper.writeValueAsString(
                LoginRequest(
                        email = "test2@email.com",
                        password = "testpassword",
                )
        )
        val response = objectMapper.readValue<ExceptionResponse>(
                mock.perform(post("/auth")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .characterEncoding("UTF-8"))
                        .andExpect(status().isBadRequest)
                        .andReturn()
                        .response
                        .contentAsString
        )
        assertThat(response.code).isEqualTo("USER_NOTFOUND")
    }

    @Test
    fun `로그인 - 비밀번호가 일치하지 않음 Account Information Mismatch Exception`() {
        val requestBody = objectMapper.writeValueAsString(
                LoginRequest(
                        email = "test@email.com",
                        password = "testpassword2",
                )
        )
        val response = objectMapper.readValue<ExceptionResponse>(
                mock.perform(post("/auth")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .characterEncoding("UTF-8"))
                        .andExpect(status().isBadRequest)
                        .andReturn()
                        .response
                        .contentAsString
        )
        assertThat(response.code).isEqualTo("ACCOUNT_INFORMATION_MISMATCH")

    }

    @Test
    fun `토큰 유효성 검사 OK`() {
        mock.perform(post("/auth/token")
                .header("Authorization", "this-is-test-token")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk)
    }

    @Test
    fun `토큰 유효성 검사 - 토큰이 잘못됨 Invalid Token Exception`() {
        val response = objectMapper.readValue<ExceptionResponse>(
                mock.perform(post("/auth/token")
                        .header("Authorization", "invalid token")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .characterEncoding("UTF-8"))
                        .andExpect(status().isUnauthorized)
                        .andReturn()
                        .response
                        .contentAsString
        )
        assertThat(response.code).isEqualTo("INVALID_TOKEN")
    }

    @Test
    fun `토큰 재발급 OK`() {
        val response = objectMapper.readValue<AccessTokenResponse>(
                mock.perform(patch("/auth")
                        .header("refreshToken", "this-is-test-token")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .characterEncoding("UTF-8"))
                        .andExpect(status().isOk)
                        .andReturn()
                        .response
                        .contentAsString
        )

        assertThat(response.accessToken).isEqualTo("this-is-test-token")
    }

    @Test
    fun `토큰 재발급 - 토큰이 잘못됨 Invalid Token Exception`() {
        val response = objectMapper.readValue<ExceptionResponse>(
                mock.perform(patch("/auth")
                        .header("refreshToken", "invalid token")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .characterEncoding("UTF-8"))
                        .andExpect(status().isUnauthorized)
                        .andReturn()
                        .response
                        .contentAsString
        )

        assertThat(response.code).isEqualTo("INVALID_TOKEN")
    }

}