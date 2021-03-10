package welper.welper.domain

import javax.persistence.*;

@Entity
class EmailCertify(

        @Id
        @Column(name = "email")
        val email: String,

        @Column(name = "authCode")
        val authCode: String,

        @Column(name="certified")
        val certified:Boolean,

)