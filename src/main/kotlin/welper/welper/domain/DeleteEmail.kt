package welper.welper.domain
import javax.persistence.Id
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "deleteEmail")
class DeleteEmail(
        @Id
        @Column(name = "email")
        val email: String,
        @Column(name = "createdAt")
        val deleteTime: LocalDateTime,
) {

}