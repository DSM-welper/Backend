package welper.welper.service

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import welper.welper.domain.EmailCertify
import welper.welper.exception.AlreadyExistAccountException
import welper.welper.exception.AuthenticationNumberMismatchException
import welper.welper.repository.EmailCertifyRepository
import welper.welper.repository.UserRepository
import java.math.BigInteger
import java.nio.charset.Charset
import java.security.MessageDigest
import java.util.*

@Service

class EmailService(
        private val javaMailSender: JavaMailSender,
        private val emailCertifyRepository: EmailCertifyRepository,
        private val userRepository: UserRepository,
) {
    private val encryptionAlgorithm = "SHA-512"
    private val characterEncoding = Charset.forName("UTF-8")

    fun send(email: String) {

        isJoinPossible(email)

        val randomCode: String = getRandomString(10)

        emailCertifyRepository.save(
                EmailCertify(
                        email = email,
                        authCode = encodingAuthCode(randomCode),
                        certified = false,
                )
        )
        val message = SimpleMailMessage()
        message.setFrom("a")
        message.setTo(email)
        message.setSubject("Welper 인증코드")
        println(randomCode)
        message.setText("인증 코드: $randomCode")
        javaMailSender.send(message)


    }

    fun approvalMail(authCode: String, email: String) {
        val emailCertify: EmailCertify = emailCertifyRepository.findByEmailAndAuthCode(email = email, authCode = encodingAuthCode(authCode))
                ?: throw AuthenticationNumberMismatchException(email)
        emailCertify.isCertified()
        emailCertifyRepository.save(emailCertify)
    }


    private fun encodingAuthCode(originalPassword: String): String {
        val messageDigest = MessageDigest.getInstance(encryptionAlgorithm)
        messageDigest.update(originalPassword.toByteArray(characterEncoding))
        return String.format("%0128x", BigInteger(1, messageDigest.digest()))
    }

    private fun getRandomString(length: Int): String {
        val charset = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return (1..length)
                .map { charset.random() }
                .joinToString("")
    }

    private fun isJoinPossible(email: String) {
        val findByEmail: EmailCertify? = emailCertifyRepository.findByEmailAndCertified(email, true)
        if (findByEmail != null)
            if (userRepository.existsByEmail(email))
                throw AlreadyExistAccountException(email)
            else {
                findByEmail.isReCertified()
                emailCertifyRepository.save(findByEmail)
            }
    }
}