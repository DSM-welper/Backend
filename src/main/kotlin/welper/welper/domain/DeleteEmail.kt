package welper.welper.domain
import javax.persistence.Id
import java.time.LocalDateTime
import javax.persistence.Column

class DeleteEmail(
        @Id
        @Column(name = "email")
        val email: String,
        @Column(name = "createdAt")
        val deleteTime: LocalDateTime,
) {

}