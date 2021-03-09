package welper.welper.service

import org.springframework.stereotype.Service
import welper.welper.domain.User
import welper.welper.repository.UserRepository
import java.math.BigInteger
import java.nio.charset.Charset
import java.security.MessageDigest


@Service

class AuthService(
        private val userRepository: UserRepository
) {
    private val encryptionAlgorithm = "SHA-512"
    private val characterEncoding = Charset.forName("UTF-8")

    fun signUp(email: String, password:String, name:String) {

        userRepository.save(
                User(
                    email=email,
                    password=encodingPassword(password),
                    name=name,
                )
        )
    }

    private fun encodingPassword(originalPassword: String): String {
        val messageDigest = MessageDigest.getInstance(encryptionAlgorithm)
        messageDigest.update(originalPassword.toByteArray(characterEncoding))
        return String.format("%0128x", BigInteger(1, messageDigest.digest()))
    }

}