package welper.welper.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.TestConstructor
import welper.welper.domain.Comments
import welper.welper.domain.User
import welper.welper.domain.attribute.Gender
import welper.welper.domain.attribute.Marry

@DataJpaTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
internal class CommentsRepositoryTest(
        val commentsRepository: CommentsRepository,
) {
    @Test
    fun `postId로 모두 조회 OK`() {
        val comments: List<Comments> = commentsRepository.findAllByPostId(1)
        assertThat(comments).map<Int> { it.postId }.containsAll(listOf(1))
    }

    @Test
    fun `없는 postId로 모두 조회`() {
        assertThat(
                commentsRepository.findAllByPostId(3)
        ).isEmpty()
    }

    @Test
    fun `postID와 부모댓글Id로 모두 조회 OK`() {
        val comments: List<Comments> = commentsRepository.findAllByPostIdAndParents(1, 0)
        assertThat(comments).map<Int> { it.postId }.containsAll(listOf(1))
    }

    @Test
    fun `없는 postID와 부모댓글 ID로 모두 조회`() {
        assertThat(
                commentsRepository.findAllByPostIdAndParents(3, 1)
        ).isEmpty()
    }

    @Test
    fun `id와 user로 검색 OK`() {
        val comments = commentsRepository.findByIdAndUser(1,
                user = User(
                        email = "test@email.com",
                        password = "e9e633097ab9ceb3e48ec3f70ee2beba41d05d5420efee5da85f97d97005727587fda33ef4ff2322088f4c79e8133cc9cd9f3512f4d3a303cbdb5bc585415a00",
                        age = 13,
                        name = "test",
                        marry = Marry.DO,
                        disorder = false,
                        gender = Gender.WOMEN
                ))
        if (comments != null)
            assertThat(comments.id).isEqualTo(1)

    }

    @Test
    fun `없는 id와 user로 검색 `() {
        assertThat(
                commentsRepository.findByIdAndUser(3,
                        user = User(
                                email = "test@email.com",
                                password = "e9e633097ab9ceb3e48ec3f70ee2beba41d05d5420efee5da85f97d97005727587fda33ef4ff2322088f4c79e8133cc9cd9f3512f4d3a303cbdb5bc585415a00",
                                age = 13,
                                name = "test",
                                marry = Marry.DO,
                                disorder = false,
                                gender = Gender.WOMEN
                        )
                )
        ).isNull()
    }

    @Test
    fun `postId로 삭제`() {
        commentsRepository.deleteAllByPostId(1)
        assertThat(commentsRepository.findAllByPostId(1)
        ).isEmpty()
    }

//    @Test
//    fun `없는 postId로 삭제`() {
//        commentsRepository.deleteAllByPostId(4)
//        assertThat(commentsRepository.findAllByPostId(4)
//        ).isEmpty()
//    }
}