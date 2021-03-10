package welper.welper.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import welper.welper.controller.request.EmailCertifyRequest
import welper.welper.service.EmailService
import javax.validation.Valid

@RestController
@RequestMapping("/mail")
class EmailController(
        private val emailService: EmailService,
) {

    @PostMapping("send")
    fun sendMail(@RequestBody @Valid request: EmailCertifyRequest)=
            emailService.send(request.email)
}