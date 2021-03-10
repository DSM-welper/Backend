package welper.welper.repository

import org.springframework.data.jpa.repository.JpaRepository
import welper.welper.domain.EmailCertify

interface EmailCertifyRepository : JpaRepository<EmailCertify,String> {
 fun findByEmailAndCertified(email: String, certified: Boolean,):EmailCertify?
}