package welper.welper.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "bookMark")
class BookMark(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int = 0,

        val email: String,

        @ManyToOne
        val openApiPost: OpenApiPost,
) {

}