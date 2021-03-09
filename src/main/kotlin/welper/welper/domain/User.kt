package welper.welper.domain

import javax.persistence.*

@Entity
@Table(name = "user")
class User(

        @Id
        @Column(name = "email")
        val email: String,

        @Column (name = "password")
        val password: String,

        @Column(name = "name")
        val name: String,

)