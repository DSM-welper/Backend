package welper.welper.service

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

internal class JwtServiceTest () {
    private val jwtService = JwtService("kotlin-love")

    @Test
    fun `토큰 생성 OK`() {
        val accessToken = jwtService.createToken("test@email.com", Token.ACCESS)
        val refreshToken = jwtService.createToken("test@email.com", Token.REFRESH)

        assertThat(accessToken).isNotBlank
        assertThat(refreshToken).isNotBlank
    }

    @Test
    fun `유효한 토큰인지 검사 OK`() {
        val accessToken = jwtService.createToken("test@email.com", Token.ACCESS)
        val isValidationToken = jwtService.isValid(accessToken)

        assertThat(isValidationToken).isTrue
    }

    @Test
    fun `토큰에서 email 추출 OK`() {
        val accessToken = jwtService.createToken("test@email.com", Token.ACCESS)
        val teacherId = jwtService.getEmail(accessToken)

        assertThat(teacherId).isEqualTo("test@email.com")
    }
}