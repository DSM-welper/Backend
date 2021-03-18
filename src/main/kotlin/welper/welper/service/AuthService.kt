package welper.welper.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import welper.welper.controller.response.LoginResponse
import welper.welper.domain.EmailCertify
import welper.welper.domain.User
import welper.welper.domain.attribute.Gender
import welper.welper.domain.attribute.Marry
import welper.welper.exception.*
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


    fun signUp(email: String, password: String, name: String, age: Int, marry: Marry, gender: Gender, disorder: Boolean) {
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
                        marry = marry,
                        gender = gender,
                        disorder = disorder,
                )
        )
    }

    fun login(email: String, password: String): LoginResponse {
        userRepository.findByIdOrNull(email) ?: throw UserNotFoundException(email)
        validateAccountInformation(email, password)

        return LoginResponse(
                accessToken = createAccessToken(email),
                refreshToken = createRefreshToken(email),
        )
    }


    private fun encodingPassword(originalPassword: String): String {
        val messageDigest = MessageDigest.getInstance(encryptionAlgorithm)
        messageDigest.update(originalPassword.toByteArray(characterEncoding))
        return String.format("%0128x", BigInteger(1, messageDigest.digest()))
    }

    private fun isJoinPossible(email: String) = userRepository.existsById(email)

    private fun createAccessToken(email: String) = jwtService.createToken(email, Token.ACCESS)

    private fun createRefreshToken(email: String) = jwtService.createToken(email, Token.REFRESH)

    fun recreateAccessToken(refreshToken: String): String {
        validateToken(refreshToken)

        val email = jwtService.getEmail(refreshToken)
        return createAccessToken(email)
    }

    private fun validateToken(token: String) {
        val isValid = jwtService.isValid(token)
        if (!isValid) throw InvalidTokenException(token)
    }

    private fun validateAccountInformation(email: String, teacherPassword: String) {
        val teacher = findUserByEmail(email)
        val encodedPassword = encodingPassword(teacherPassword)
        validateSamePassword(teacher.password, encodedPassword)
    }

    private fun findUserByEmail(email: String) =
            userRepository.findByIdOrNull(email) ?: throw AccountInformationMismatchException(email, "찾은 정보 없음")

    private fun validateSamePassword(requestPassword: String, findPassword: String) {
        if (requestPassword != findPassword)
            throw AccountInformationMismatchException(requestPassword, findPassword)
    }

}