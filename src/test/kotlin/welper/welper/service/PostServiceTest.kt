package welper.welper.service

import com.nhaarman.mockitokotlin2.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.repository.findByIdOrNull
import welper.welper.domain.Comments
import welper.welper.domain.Post
import welper.welper.domain.User
import welper.welper.domain.attribute.Gender
import welper.welper.domain.attribute.Marry
import welper.welper.repository.CommentsRepository
import welper.welper.repository.PostRepository
import welper.welper.repository.UserRepository
import java.time.LocalDateTime

internal class PostServiceTest {
    @Test
    fun `post 만들기 ok`() {
        postService.postCreate(
                token = "this-is-test-token",
                title = "test",
                content = "a",
                category = "test",
                createdAt = LocalDateTime.now()
        )
    }

    private val user = User(
            email =
            "test@email.com",
            password = "e9e633097ab9ceb3e48ec3f70ee2beba41d05d5420efee5da85f97d97005727587fda33ef4ff2322088f4c79e8133cc9cd9f3512f4d3a303cbdb5bc585415a00",
            age = 13,
            name = "test",
            marry = Marry.DO,
            disorder = false,
            gender = Gender.WOMEN,
    )

    private val post = Post(
            id = 1,
            title = "test",
            content = "test 좀 합니다.",
            createdAt = LocalDateTime.now(),
            user = user,
            category = "test",
    )

    private val comments = Comments(
            id = 1,
            sequence = 1,
            comments = "2",
            postId = 1,
            user = user,
            depts = 0,
            parents = 0
    )

    private val postService = PostService(
            jwtService = mock {
                on { createToken(eq("test@email.com"), any()) } doReturn "this-is-test-token"
                on { getEmail(eq("this-is-test-token")) } doReturn "test@email.com"
                on { isValid(eq("this-is-test-token")) } doReturn true
            },
            userRepository = mock {
                on {
                    findByEmail("test@email.com") }doReturn user
            },
            postRepository = mock {
                on { findAllByCategory("test") } doReturn listOf(post)
                on { findAllByUser(user = user) } doReturn listOf(post)
                on { findByIdAndUser(id = 1, user = user) } doReturn post
            },
            commentsRepository = mock {
                on { findAllByPostIdAndParents(1, 0) } doReturn listOf(comments)
                on { findByIdAndUser(1, user) } doReturn comments
                on { findAllByPostId(1) } doReturn listOf(comments)
                on { deleteAllByPostId(1) }
            },
    )
}