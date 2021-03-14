package welper.welper.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name ="post")
class Post(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        val id: Int = 0,

        @Column(name = "title")
        val title: String,

        @Column(name = "content")
        val content: String,

        @Column(name = "createdAt")
        val createdAt: LocalDateTime,

        @ManyToOne
        @JoinColumn(name = "user", referencedColumnName = "email")
        var user: User,

        @Column(name = "category")
        val category: String,
)