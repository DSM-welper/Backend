package welper.welper.service

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import welper.welper.controller.request.EmailCertifyRequest
import welper.welper.domain.EmailCertify
import welper.welper.repository.EmailCertifyRepository
import java.math.BigInteger
import java.nio.charset.Charset
import java.security.MessageDigest
import java.util.*

@Service

class EmailService (
        private val javaMailSender:JavaMailSender,
        private val emailCertifyRepository: EmailCertifyRepository,
){
    private val encryptionAlgorithm = "SHA-512"
    private val characterEncoding = Charset.forName("UTF-8")

    fun getRandomString(length: Int) : String {
        val charset = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return (1..length)
                .map { charset.random() }
                .joinToString("")
    }

    fun send(email:String){
        val randomCode :String = getRandomString(10)

        emailCertifyRepository.save(
            EmailCertify(
                    email=email,
                    authCode = encodingPassword(randomCode),
            )
        )

                val message = SimpleMailMessage()
                message.setFrom("ahn479512@gmail.com")
                message.setTo(email)
                message.setSubject("Welper 인증코드")
                message.setText("인증 코드:"+randomCode)
                javaMailSender.send(message)


    }
    private fun encodingPassword(originalPassword: String): String {
        val messageDigest = MessageDigest.getInstance(encryptionAlgorithm)
        messageDigest.update(originalPassword.toByteArray(characterEncoding))
        return String.format("%0128x", BigInteger(1, messageDigest.digest()))
    }
}