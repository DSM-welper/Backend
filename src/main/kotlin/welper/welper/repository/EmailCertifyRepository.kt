package welper.welper.repository

import org.springframework.data.jpa.repository.JpaRepository
import welper.welper.domain.EmailCertify

interface EmailCertifyRepository : JpaRepository<EmailCertify,String> {
 fun findByEmailAndCertified(email: String, certified: Boolean,):EmailCertify?
 fun findByEmailAndAuthCode(email: String,authCode: String):EmailCertify?
 fun existsByEmailAndCertified(email: String, certified: Boolean):Boolean?

}