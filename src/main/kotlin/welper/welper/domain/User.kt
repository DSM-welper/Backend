package welper.welper.domain

import welper.welper.domain.attribute.Gender
import welper.welper.domain.attribute.Marry
import welper.welper.domain.converter.GenderConverter
import welper.welper.domain.converter.MarryConverter
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
        marry: Marry,
        disorder:Boolean,
        gender: Gender,
) {
    @Column(name = "disorder")
    var disorder=disorder
        private set

    @Column(name = "name")
    var name = name
        private set

    @Column(name = "marry")
    @Convert(converter = MarryConverter::class)
    var marry = marry
        private set

    @Column(name = "gender")
    @Convert(converter = GenderConverter::class)
    var gender = gender
        private set

    @Column(name = "age")
    var age = age
        private set

    fun updateMyPage(name: String?, marry: Marry?, gender: Gender?, age: Int?,disorder: Boolean?) {
        if(name!=null)
        this.name = name
        if(disorder!=null)
        this.disorder = disorder
        if(gender!=null)
        this.gender = gender
        if(age!=null)
        this.age = age
        if(marry!=null)
        this.marry = marry
    }

}