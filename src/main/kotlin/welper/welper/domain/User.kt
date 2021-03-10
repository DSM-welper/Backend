package welper.welper.domain

import javax.persistence.*

@Entity
@Table(name = "user")
class User(

        @Id
        @Column(name = "email")
        val email: String,

        @Column(name = "password")
        val password: String,

        @Column(name = "name")
        val name: String,

        @Column(name = "isMarry")
        val isMerry: Boolean,

        @Column(name = "isWomen")
        val isWomen: Boolean,

        @Column(name = "age")
        val age: Int,

//        @Column(name = "emailCertifyId")
//        @OneToOne(mappedBy = "EmailCertify")
//        val emailCertify: EmailCertify?,

        )