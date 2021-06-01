package welper.welper.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.TestConstructor
import welper.welper.domain.User
import welper.welper.domain.attribute.Gender
import welper.welper.domain.attribute.Marry

@DataJpaTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
internal class PostRepositoryTest(
        val postRepository: PostRepository,
) {

    @Test
    fun `id와 user로 검색 ok`() {
        val post = postRepository.findByIdAndUser(1,
                user = User(
                        email = "test@email.com",
                        password = "e9e633097ab9ceb3e48ec3f70ee2beba41d05d5420efee5da85f97d97005727587fda33ef4ff2322088f4c79e8133cc9cd9f3512f4d3a303cbdb5bc585415a00",
                        age = 13,
                        name = "test",
                        marry = Marry.DO,
                        disorder = false,
                        gender = Gender.WOMEN
                ))
        if (post != null)
            assertThat(post.id).isEqualTo(1)
    }

    @Test
    fun `일치하지않는 id와 user로 검색`() {
        assertThat(postRepository.findByIdAndUser(2,
                user = User(
                        email = "test@email.com",
                        password = "e9e633097ab9ceb3e48ec3f70ee2beba41d05d5420efee5da85f97d97005727587fda33ef4ff2322088f4c79e8133cc9cd9f3512f4d3a303cbdb5bc585415a00",
                        age = 13,
                        name = "test",
                        marry = Marry.DO,
                        disorder = false,
                        gender = Gender.WOMEN
                ))).isNull()
    }

    @Test
    fun `category로 모두 검색 ok`() {
        val post = postRepository.findAllByCategory("test")
        assertThat(post).map<Int> { it?.id }.containsAll(listOf(1))
    }

    @Test
    fun `없는 category로 모두 검색`() {
        assertThat(
                postRepository.findAllByCategory("head3")
        ).isEmpty()
    }

    @Test
    fun `user로 모두 검색 ok`() {
        val post = postRepository.findAllByUser(
                user = User(
                        email = "test@email.com",
                        password = "e9e633097ab9ceb3e48ec3f70ee2beba41d05d5420efee5da85f97d97005727587fda33ef4ff2322088f4c79e8133cc9cd9f3512f4d3a303cbdb5bc585415a00",
                        age = 13,
                        name = "test",
                        marry = Marry.DO,
                        disorder = false,
                        gender = Gender.WOMEN
                ))
        assertThat(post).map<Int> { it?.id }.containsAll(listOf(1))
    }

    @Test
    fun `없는 user로 모두 검색`() {
        assertThat(postRepository.findAllByUser(
                user = User(
                        email = "test23@email.com",
                        password = "e9e633097ab9ceb3e48ec3f70ee2beba41d05d5420efee5da85f97d97005727587fda33ef4ff2322088f4c79e8133cc9cd9f3512f4d3a303cbdb5bc585415a00",
                        age = 13,
                        name = "test",
                        marry = Marry.DO,
                        disorder = false,
                        gender = Gender.WOMEN
                ))).isEmpty()
    }
}