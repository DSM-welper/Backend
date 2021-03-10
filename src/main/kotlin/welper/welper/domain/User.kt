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
        age: Int,
        name: String,
        isMarry: Boolean,
        isWomen: Boolean,
//        @Column(name = "emailCertifyId")
//        @OneToOne(mappedBy = "EmailCertify")
//        val emailCertify: EmailCertify?,
) {

    @Column(name = "name")
    var name = name
        private set

    @Column(name = "isMarry")
    var isMarry = isMarry
        private set

    @Column(name = "isWomen")
    var isWomen = isWomen
        private set

    @Column(name = "age")
    var age = age
        private set

     fun updateMyPage(name:String,isMarry: Boolean,isWomen: Boolean,age: Int){
            this.name = name
            this.isWomen = isWomen
            this.age =age
            this.isMarry = isMarry
    }

}