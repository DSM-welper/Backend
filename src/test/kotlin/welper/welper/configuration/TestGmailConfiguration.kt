package welper.welper.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl

@Configuration
class TestGmailConfiguration(
        @Value("\${spring.mail.host}")
        val MAIL_HOST: String,
        @Value("\${spring.mail.port}")
        val MAIL_PORT: Int,
        @Value("\${spring.mail.username}")
        val MAIL_USERNAME: String,
        @Value("\${spring.mail.password}")
        val MAIL_PASSWORD: String,
) {
    @Bean
    fun testJavaMailSender(): JavaMailSender {
        val javaMailSender = JavaMailSenderImpl()
        javaMailSender.host = MAIL_HOST
        javaMailSender.port = MAIL_PORT
        javaMailSender.username = MAIL_USERNAME
        javaMailSender.password = MAIL_PASSWORD
        val props = javaMailSender.javaMailProperties
        props["mail.transport.protocol"] = "smtp"
        props["mail.smtp.auth"] = "true"
        props["mail.smtp.ssl.trust"] = "smtp.gmail.com"
        props["mail.smtp.starttls.enable"] = "true"

        javaMailSender.javaMailProperties = props
        return javaMailSender
    }
}