package welper.welper.service

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import welper.welper.domain.Comments
import welper.welper.domain.Post
import welper.welper.domain.User
import welper.welper.domain.attribute.Gender
import welper.welper.domain.attribute.Marry
import welper.welper.exception.CommentsNotFoundException
import welper.welper.exception.PostNotFoundException
import java.time.LocalDateTime

internal class CommentsServiceTest {
    @Test
    fun `댓글 쓰기`() {
        commentsService.commentsWrite(
                token = "this-is-test-token",
                postId = 1,
                content = "나야",
        )
    }

    @Test
    fun `대댓글 쓰기`() {
        commentsService.commentsParents(
                token = "this-is-test-token",
                postId = 1,
                content = "나야",
                commentsId = 1,
        )
    }

    @Test
    fun `대댓글 commentsId&&postId 오류`() {
        assertThrows<CommentsNotFoundException> {
            commentsService.commentsParents(
                    token = "this-is-test-token",
                    postId = 1,
                    content = "나야",
                    commentsId = 3,
            )
        }
    }

    @Test
    fun `대댓글 postId 오류`() {
        assertThrows<PostNotFoundException> {
            commentsService.commentsParents(
                    token = "this-is-test-token",
                    postId = 4,
                    content = "나야",
                    commentsId = 1,
            )
        }
    }

    @Test
    fun `댓글 삭제`() {
        commentsService.commentsDelete(
                token = "this-is-test-token",
                postId = 1,
                commentsId = 1,
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
    private val commentsService = CommentsService(
            jwtService = mock {
                on { createToken(eq("test@email.com"), any()) } doReturn "this-is-test-token"
                on { getEmail(eq("this-is-test-token")) } doReturn "test@email.com"
                on { isValid(eq("this-is-test-token")) } doReturn true
            },
            userRepository = mock {
                on { findByEmail("test@email.com") } doReturn user
            },
            postRepository = mock {
                on { findAllByCategory("test") } doReturn listOf(post)
                on { findAllByUser(user = user) } doReturn listOf(post)
                on { findByIdAndUser(id = 1, user = user) } doReturn post
                on { findPostById(1) } doReturn post
                on { findAll() } doReturn listOf(post)
            },
            commentRepository = mock {
                on { findAllByPostIdAndParents(1, 0) } doReturn listOf(comments)
                on { findByIdAndUser(1, user) } doReturn comments
                on { findAllByPostId(1) } doReturn listOf(comments)
                on { findCommentsById(1) } doReturn comments
            },
    )
}
