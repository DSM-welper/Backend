package welper.welper.domain

import javax.persistence.*;

@Entity
@Table(name = "email_certify")
class EmailCertify(
        @Id
        @Column(name = "email")
        val email: String,
        @Column(name = "authCode")
        val authCode: String,
        certified: Boolean
) {


    @Column(name = "certified", nullable = false, length = 555)
    var certified = certified
        private set

    fun isCertified(certified: Boolean) {
        this.certified = true
    }

}