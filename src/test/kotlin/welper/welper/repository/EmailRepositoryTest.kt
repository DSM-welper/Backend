package welper.welper.repository

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.`as`
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.TestConstructor
import welper.welper.domain.EmailCertify

@DataJpaTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
internal class EmailRepositoryTest(
        val emailCertifyRepository: EmailCertifyRepository,
) {

    @Test
    fun `email이랑 certified로 검색 OK`() {
        val emailCertify = emailCertifyRepository.findByEmailAndCertified("test@email.com", true)
        if (emailCertify != null)
            Assertions.assertThat(emailCertify.email).isEqualTo("test@email.com")
    }

    @Test
    fun `없는 email이랑 certifed로 검색`() {
        assertThat(
                emailCertifyRepository.findByEmailAndCertified("test@email.com", false)
        ).isNull()
    }

    @Test
    fun `email이랑 authcode 검색`() {
        val emailCertify = emailCertifyRepository.findByEmailAndAuthCode("test@email.com", "6170e8244d4c94f6a695d440f0c96f9ef5a946ae7dc5aca3a6bfdfcd8938630f9f04e5ed41d20315c25cf2d747b5b1c6491d732bf7f3d0ed61f6857ef379fa79")
        if (emailCertify != null)
            Assertions.assertThat(emailCertify.email).isEqualTo("test@email.com")
    }

    @Test
    fun `없는 email 이랑 authcode 검색`() {
        assertThat(emailCertifyRepository.findByEmailAndAuthCode("test3@email.com", "6170e8244d4c94f6a695d440f0c96f9ef5a946ae7dc5aca3a6bfdfcd8938630f9f04e5ed41d20315c25cf2d747b5b1c6491d732bf7f3d0ed61f6857ef379fa79")
        ).isNull()
    }

    @Test
    fun `email이랑 허가여부로 있는지 확인`() {
        if (emailCertifyRepository.existsByEmailAndCertified("test@email.com", true) == true)
            println("통과")
    }
}