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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.transaction.annotation.Transactional
import welper.welper.controller.request.CommentsRequest
import welper.welper.controller.request.LoginRequest
import welper.welper.controller.response.CategoryListPostResponse
import welper.welper.controller.response.LoginResponse
import welper.welper.exception.handler.ExceptionResponse
import java.lang.Exception

@Suppress("DEPRECATION")//사용되지 않는 경고 비활성화
@SpringBootTest
@AutoConfigureMockMvc
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Transactional

internal class CommentsControllerIntegrationTest(
        private val mock: MockMvc,
        private val objectMapper: ObjectMapper,
) {
    @Test
    fun `댓글 OK`() {
        val requestBody = objectMapper.writeValueAsString(
                CommentsRequest(
                        contents = "1"
                )
        )
        mock.perform(MockMvcRequestBuilders.post("/comments/1")
                .content(requestBody)
                .header("Authorization", "this-is-test-token")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()
                .response
                .contentAsString
    }

    @Test
    fun `없는 post 댓글 exception`() {
        val requestBody = objectMapper.writeValueAsString(
                CommentsRequest(
                        contents = "1"
                )
        )
        val response = objectMapper.readValue<ExceptionResponse>(
                mock.perform(MockMvcRequestBuilders.post("/comments/4")
                        .content(requestBody)
                        .header("Authorization", "this-is-test-token")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .characterEncoding("UTF-8"))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest)
                        .andReturn()
                        .response
                        .contentAsString
        )
        Assertions.assertThat(response.code).isEqualTo("POST_NOTFOUND")
    }

    @Test
    fun `대댓글 ok`() {
        val requestBody = objectMapper.writeValueAsString(
                CommentsRequest(
                        contents = "1"
                )
        )
        mock.perform(MockMvcRequestBuilders.post("/comments/1/1")
                .content(requestBody)
                .header("Authorization", "this-is-test-token")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()
                .response
                .contentAsString
    }

    @Test
    fun `없는 post 대댓글 exception`() {
        val requestBody = objectMapper.writeValueAsString(
                CommentsRequest(
                        contents = "1"
                )
        )
        val response = objectMapper.readValue<ExceptionResponse>(
                mock.perform(MockMvcRequestBuilders.post("/comments/3")
                        .content(requestBody)
                        .header("Authorization", "this-is-test-token")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .characterEncoding("UTF-8"))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest)
                        .andReturn()
                        .response
                        .contentAsString
        )
        Assertions.assertThat(response.code).isEqualTo("POST_NOTFOUND")
    }

    @Test
    fun `없는 comment 대댓글 exception`() {
        val requestBody = objectMapper.writeValueAsString(
                CommentsRequest(
                        contents = "1"
                )
        )
        val response = objectMapper.readValue<ExceptionResponse>(
                mock.perform(MockMvcRequestBuilders.post("/comments/1/2")
                        .content(requestBody)
                        .header("Authorization", "this-is-test-token")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .characterEncoding("UTF-8"))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest)
                        .andReturn()
                        .response
                        .contentAsString
        )
        Assertions.assertThat(response.code).isEqualTo("COMMENTS_NOTFOUND")
    }

    @Test
    fun `댓글 삭제 ok`() {
        mock.perform(delete("/comments/1/1")
                .header("Authorization", "this-is-test-token")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()
                .response
                .contentAsString
    }
    @Test
    fun `권한 없는 댓글 삭제 exception`() {
        val response = objectMapper.readValue<ExceptionResponse>(
                mock.perform(delete("/comments/1/2")
                        .header("Authorization", "this-is-test-token")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .characterEncoding("UTF-8"))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest)
                        .andReturn()
                        .response
                        .contentAsString
        )
        Assertions.assertThat(response.code).isEqualTo("COMMENTS_NOTFOUND")
    }
}