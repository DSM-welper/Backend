package welper.welper.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.TestConstructor
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.transaction.annotation.Transactional
import welper.welper.controller.request.PostRequest
import welper.welper.controller.request.SearchPostRequest
import welper.welper.exception.handler.ExceptionResponse
import welper.welper.controller.response.PostListResponse
import welper.welper.controller.response.PostResponse
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

@Suppress("DEPRECATION")//사용되지 않는 경고 비활성화
@SpringBootTest
@AutoConfigureMockMvc
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Transactional
internal class PostControllerIntegrationTest(
        private val mock: MockMvc,
        private val objectMapper: ObjectMapper,
) {
    @Test
    fun `포스트 만들기 ok`() {
        val requestBody = objectMapper.writeValueAsString(
                PostRequest(
                        title = "테스트 어려워요",
                        category = "test",
                        content = "테스트 어려워요"
                )
        )
        mock.perform(MockMvcRequestBuilders.post("/post")
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
    fun `포스트 만들기 토큰 오류`() {
        val requestBody = objectMapper.writeValueAsString(
                PostRequest(
                        title = "테스트 어려워요",
                        category = "test",
                        content = "테스트 어려워요"
                )
        )
        val response = objectMapper.readValue<ExceptionResponse>(
                mock.perform(MockMvcRequestBuilders.post("/post")
                        .header("Authorization", "this-is-test-token2")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .characterEncoding("UTF-8"))
                        .andExpect(MockMvcResultMatchers.status().isUnauthorized)
                        .andReturn()
                        .response
                        .contentAsString

        )
        assertThat(response.code).isEqualTo("INVALID_TOKEN")
    }

    @Test
    fun `post 게시물 검색 ok`() {
        val requestBody = objectMapper.writeValueAsString(
                SearchPostRequest(
                        content = "head"
                )
        )
        val response = objectMapper.readValue<PostListResponse>(
                mock.perform(get("/post/search")
                        .header("Authorization", "this-is-test-token")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .characterEncoding("UTF-8"))
                        .andExpect(MockMvcResultMatchers.status().isOk)
                        .andReturn()
                        .response
                        .contentAsString

        )
        assertThat(response.post).map<String> { it.title }.containsAll(listOf("head"))
    }

    @Test
    fun `post 게시물 검색 토큰 에러`() {
        val requestBody = objectMapper.writeValueAsString(
                SearchPostRequest(
                        content = "head"
                )
        )
        val response = objectMapper.readValue<ExceptionResponse>(
                mock.perform(get("/post/search")
                        .header("Authorization", "this-is-test-token2")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .characterEncoding("UTF-8"))
                        .andExpect(MockMvcResultMatchers.status().isUnauthorized)
                        .andReturn()
                        .response
                        .contentAsString

        )
        assertThat(response.code).isEqualTo("INVALID_TOKEN")
    }

    @Test
    fun `post삭제 ok`() {
        mock.perform(delete("/post/1")
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
    fun `post삭제 게시물 못찾음`() {
        val response = objectMapper.readValue<ExceptionResponse>(
                mock.perform(delete("/post/3")
                        .header("Authorization", "this-is-test-token")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .characterEncoding("UTF-8"))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest)
                        .andReturn()
                        .response
                        .contentAsString
        )
        assertThat(response.code).isEqualTo("POST_NOTFOUND")
    }

    @Test
    fun `post삭제 유저가 다름`() {
        val response = objectMapper.readValue<ExceptionResponse>(
                mock.perform(delete("/post/2")
                        .header("Authorization", "this-is-test-token")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .characterEncoding("UTF-8"))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest)
                        .andReturn()
                        .response
                        .contentAsString
        )
        assertThat(response.code).isEqualTo("POST_NOTFOUND")
    }
    @Test
    fun `post 읽기 ok`() {
        val response = objectMapper.readValue<PostResponse>(
                mock.perform(get("/post/detail/1")
                        .header("Authorization", "this-is-test-token")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .characterEncoding("UTF-8"))
                        .andExpect(MockMvcResultMatchers.status().isOk)
                        .andReturn()
                        .response
                        .contentAsString
        )
        assertThat(response.title).isNotBlank
        assertThat(response.content).isNotBlank
        assertThat(response.category).isNotBlank
        assertThat(response.writer).isNotBlank
        assertThat(response.createdAt)
        assertThat(response.comment).map<String> {it.commentWriter}.containsAll(listOf("test"))
    }
    @Test
    fun `없는 post 읽기 에러`() {
        val response = objectMapper.readValue<ExceptionResponse>(
                mock.perform(get("/post/detail/3")
                        .header("Authorization", "this-is-test-token")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .characterEncoding("UTF-8"))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest)
                        .andReturn()
                        .response
                        .contentAsString
        )
        assertThat(response.code).isEqualTo("POST_NOTFOUND")
    }
    @Test
    fun `post list 읽기 ok`() {
        val response = objectMapper.readValue<PostListResponse>(
                mock.perform(get("/post")
                        .header("Authorization", "this-is-test-token")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .characterEncoding("UTF-8"))
                        .andExpect(MockMvcResultMatchers.status().isOk)
                        .andReturn()
                        .response
                        .contentAsString
        )
        assertThat(response.post).map<String> { it.title }.containsAll(listOf("head"))
    }///category{category}
    @Test
    fun `post category 읽기 ok`() {
        val response = objectMapper.readValue<PostListResponse>(
                mock.perform(get("/post/category/test")
                        .header("Authorization", "this-is-test-token")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .characterEncoding("UTF-8"))
                        .andExpect(MockMvcResultMatchers.status().isOk)
                        .andReturn()
                        .response
                        .contentAsString
        )
        assertThat(response.post).map<String> { it.title }.containsAll(listOf("head"))
    }
    @Test
    fun `자신의 post 읽기 ok`() {
        val response = objectMapper.readValue<PostListResponse>(
                mock.perform(get("/post/category/test")
                        .header("Authorization", "this-is-test-token")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .characterEncoding("UTF-8"))
                        .andExpect(MockMvcResultMatchers.status().isOk)
                        .andReturn()
                        .response
                        .contentAsString
        )
        assertThat(response.post).map<String> {it.title}.containsAll(listOf("head"))
    }
}