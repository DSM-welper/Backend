package welper.welper.service

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.data.repository.findByIdOrNull
import welper.welper.controller.response.LoginResponse
import welper.welper.domain.EmailCertify
import welper.welper.domain.User
import welper.welper.domain.attribute.Gender
import welper.welper.domain.attribute.Marry
import welper.welper.exception.*
import welper.welper.repository.EmailCertifyRepository
import welper.welper.repository.UserRepository

internal class AuthServiceTest {
    @Test
    fun `로그인OK`() {
        val actual = authService.login(
                email = "test@email.com",
                password = "testpassword"
        )
        val expected = LoginResponse(
                accessToken = "this-is-test-token",
                refreshToken = "this-is-test-token",
        )
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `옳지 않은 이메일로 로그인`() {
        assertThrows<UserNotFoundException> {
            authService.login(
                    email = "test22@email.com",
                    password = "testpassword"
            )
        }
    }

    @Test
    fun `옳지 않은 비밀번호로 로그인 시도`() {
        assertThrows<AccountInformationMismatchException> {
            authService.login(
                    email = "test@email.com",
                    password = "2222222222222",
            )
        }
    }

    @Test
    fun `회원가입OK`() {
        authService.signUp(
                email = "test@email.com",
                password = "testpassword",
                name = "test",
                age = 13,
                marry = Marry.DO,
                gender = Gender.WOMEN,
                disorder = false,
        )
    }

    @Test
    fun `인증되지 않은 회원가입 시도`() {
        assertThrows<NonExistEmailCertifyException> {
            authService.signUp(
                    email = "test2@email.com",
                    password = "testpassword",
                    name = "test",
                    age = 13,
                    marry = Marry.DO,
                    gender = Gender.WOMEN,
                    disorder = false,
            )
        }
    }

    //    @Test
//    fun `이미 있는 이메일로 회원가입 시도`() {
//        assertThrows<AlreadyExistAccountException> {
//            authService.signUp(
//                    email = "test2@email.com",
//                    password = "testpassword",
//                    name = "test",
//                    age = 13,
//                    marry = Marry.DO,
//                    gender = Gender.WOMEN,
//                    disorder = false,
//            )
//        }
//    }
    @Test
    fun `토큰 재발급`() {
        authService.recreateAccessToken("this-is-test-token")
    }

    @Test
    fun `토큰 유효성 검사`() {
        authService.validateToken("this-is-test-token")
    }

    @Test
    fun `토큰 오류`() {
        assertThrows<InvalidTokenException> {
            authService.recreateAccessToken("this-is-test-token2")
        }
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
    private val emailCertify = EmailCertify(
            email = "test@email.com",
            authCode = "6170e8244d4c94f6a695d440f0c96f9ef5a946ae7dc5aca3a6bfdfcd8938630f9f04e5ed41d20315c25cf2d747b5b1c6491d732bf7f3d0ed61f6857ef379fa79",
            certified = true
    )
    private val authService = AuthService(
            userRepository = mock {
                on { findByEmail("test@email.com") } doReturn user
            },
            jwtService = mock {
                on { createToken(eq("test@email.com"), any()) } doReturn "this-is-test-token"
                on { getEmail(eq("this-is-test-token")) } doReturn "test@email.com"
                on { isValid(eq("this-is-test-token")) } doReturn true
            },
            emailCertifyRepository = mock {
                on { findByEmailAndCertified("test@email.com", true) } doReturn emailCertify
                on { findByEmailAndCertified("test2@email.com", false) } doReturn emailCertify
                on { findByEmailAndAuthCode("test@email.com", "6170e8244d4c94f6a695d440f0c96f9ef5a946ae7dc5aca3a6bfdfcd8938630f9f04e5ed41d20315c25cf2d747b5b1c6491d732bf7f3d0ed61f6857ef379fa79") } doReturn emailCertify
                on { existsByEmailAndCertified("test@email.com", true) } doReturn true
                on { existsByEmailAndCertified("test2@email.com", true) } doReturn false

            },
    )
}