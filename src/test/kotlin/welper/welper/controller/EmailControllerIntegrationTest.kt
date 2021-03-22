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
import welper.welper.controller.request.ApproveRequest
import welper.welper.controller.request.EmailCertifyRequest
import welper.welper.controller.request.LoginRequest
import welper.welper.controller.response.LoginResponse
import welper.welper.exception.handler.ExceptionResponse

@Suppress("DEPRECATION")//사용되지 않는 경고 비활성화
@SpringBootTest
@AutoConfigureMockMvc
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Transactional
internal class EmailControllerIntegrationTest(
        private val mock: MockMvc,
        private val objectMapper: ObjectMapper,
) {
    @Test
    fun `이메일 인증 보내기`() {
        val requestBody = objectMapper.writeValueAsString(
                EmailCertifyRequest(
                        mail = "test3@email.com",
                )
        )
        mock.perform(MockMvcRequestBuilders.post("/mail")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `이미 가입한 이메일`() {
        val requestBody = objectMapper.writeValueAsString(
                EmailCertifyRequest(
                        mail = "test@email.com",
                )
        )
        val response = objectMapper.readValue<ExceptionResponse>(
                mock.perform(MockMvcRequestBuilders.post("/mail")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .characterEncoding("UTF-8"))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest)
                        .andReturn()
                        .response
                        .contentAsString
        )
        Assertions.assertThat(response.code).isEqualTo("ALREADY_EXIST_ACCOUNT")
    }

    @Test
    fun `이메일 인증 ok`() {
        val requestBody = objectMapper.writeValueAsString(
                ApproveRequest(
                        authCode = "jiFw1McPDd",
                        mail = "test2@email.com"
                )
        )
        mock.perform(MockMvcRequestBuilders.patch("/mail")
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
    fun `이메일 인증이 됬거나, 인증코드오류 ok`() {
        val requestBody = objectMapper.writeValueAsString(
                ApproveRequest(
                        authCode = "jiFw1McPDd2",
                        mail = "test@email.com"
                )
        )
        val response = objectMapper.readValue<ExceptionResponse>(
                mock.perform(MockMvcRequestBuilders.patch("/mail")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
                .andReturn()
                .response
                .contentAsString
        )
        Assertions.assertThat(response.code).isEqualTo("AUTHENTICATION_NUMBER_MISMATCH")
    }
}