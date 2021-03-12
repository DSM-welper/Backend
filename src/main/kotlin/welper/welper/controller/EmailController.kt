package welper.welper.controller

import org.springframework.web.bind.annotation.*
import welper.welper.controller.request.ApproveRequest
import welper.welper.controller.request.EmailCertifyRequest
import welper.welper.service.EmailService
import javax.validation.Valid

@RestController
@RequestMapping("/mail")
class EmailController(
        private val emailService: EmailService,
) {

    @PostMapping
    fun sendMail(@RequestBody @Valid request: EmailCertifyRequest)=
            emailService.send(request.mail)

    @PatchMapping
    fun approvalMail(@RequestBody @Valid request: ApproveRequest)=
            emailService.approvalMail(request.authCode,request.mail)
}