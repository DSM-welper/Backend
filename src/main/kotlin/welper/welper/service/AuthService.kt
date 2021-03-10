package welper.welper.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import welper.welper.controller.response.LoginResponse
import welper.welper.domain.EmailCertify
import welper.welper.domain.User
import welper.welper.exception.AlreadyExistAccountException
import welper.welper.exception.AuthenticationNumberMismatchException
import welper.welper.exception.NonExistEmailCertifyException
import welper.welper.exception.UserNotFoundException
import welper.welper.repository.EmailCertifyRepository
import welper.welper.repository.UserRepository
import java.math.BigInteger
import java.nio.charset.Charset
import java.security.MessageDigest


@Service
class AuthService(
        private val userRepository: UserRepository,
        private val jwtService: JwtService,
        private val emailCertifyRepository: EmailCertifyRepository,
) {
    private val encryptionAlgorithm = "SHA-512"
    private val characterEncoding = Charset.forName("UTF-8")


    fun signUp(email: String, password: String, name: String, age: Int, isMarry: Boolean, isWomen: Boolean) {
        val emailCertify: EmailCertify = emailCertifyRepository.findByEmailAndCertified(email, true)
                ?: throw NonExistEmailCertifyException(email)
        val isJoinPossible = isJoinPossible(email)
        if (isJoinPossible) throw AlreadyExistAccountException(email)
        userRepository.save(
                User(
                        email = email,
                        password = encodingPassword(password),
                        name = name,
                        age = age,
                        isMarry = isMarry,
                        isWomen = isWomen,

                        )
        )
    }

    fun login(email: String, password: String): LoginResponse {
        userRepository.findByIdOrNull(email) ?: throw UserNotFoundException(email)
        val user: User = userRepository.findByEmailAndPassword(email, encodingPassword(password))
                ?: throw AuthenticationNumberMismatchException(email)
        return LoginResponse(
                accessToken = createAccessToken(user.email),
                refreshToken = createRefreshToken(user.email),
        )
    }


    private fun encodingPassword(originalPassword: String): String {
        val messageDigest = MessageDigest.getInstance(encryptionAlgorithm)
        messageDigest.update(originalPassword.toByteArray(characterEncoding))
        return String.format("%0128x", BigInteger(1, messageDigest.digest()))
    }

    private fun isJoinPossible(email: String) = !userRepository.existsById(email)

    private fun createAccessToken(teacherId: String) = jwtService.createToken(teacherId, Token.ACCESS)

    private fun createRefreshToken(teacherId: String) = jwtService.createToken(teacherId, Token.REFRESH)


}