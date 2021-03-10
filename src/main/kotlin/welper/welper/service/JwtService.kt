package welper.welper.service

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

@Service
class JwtService(
        @Value("\${JWT_SECRET}")
        private val secretKey: String,
) {

    private val base64SecretKey = Base64.getEncoder().encodeToString(secretKey.toByteArray())
    private val signatureAlgorithm = SignatureAlgorithm.HS256

    fun createToken(email: String, tokenType: Token): String =
            Jwts.builder()
                    .setHeaderParam("typ", "JWT")
                    .claim("id", email)
                    .setExpiration(Date(System.currentTimeMillis() + tokenType.expirationTime))
                    .signWith(signatureAlgorithm, base64SecretKey)
                    .compact()
    fun getEmail(token: String): String =
            Jwts.parser()
                    .setSigningKey(base64SecretKey)
                    .parseClaimsJws(token)
                    .body
                    .get("id", String::class.java)

    fun isValid(token: String) =
            try {
                val currentTime = Date()
                val expirationTime = Jwts.parser()
                        .setSigningKey(base64SecretKey)
                        .parseClaimsJws(token)
                        .body
                        .expiration
                expirationTime.after(currentTime)
            } catch (e: Exception) { false }

}