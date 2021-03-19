package welper.welper.service.test

import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service
import welper.welper.service.JwtService
import welper.welper.service.Token

@Primary
@Service
class TestJwtService : JwtService("token-token") {

    override fun createToken(email: String, tokenType: Token) = "this-is-test-token"

    override fun getEmail(token: String): String {
        isValid(token)
        return "test@email.com"
    }

    override fun isValid(token: String) = token == "this-is-test-token"
}