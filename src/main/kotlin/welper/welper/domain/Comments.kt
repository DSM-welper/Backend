package welper.welper.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "comments")
class Comments(

        id: Int = 0,

        @Column(name = "createdAt")
        val createdAt: LocalDateTime,

        @Column(name = "parents")
        val parents: Int,

        @Column(name = "depth")
        val depts: Int,

        @Column(name = "comments")
        val comments: String,

        @Column(name = "postId")
        val postId: Int,

        @Column(name = "email")
        var email: String,

        @Column(name = "writer")
        val writer: String,

        sequence: Int = 0,
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id = id
        private set

    @Column(name = "sequence", nullable = false, length = 555)

    var sequence = sequence
        private set


    fun updateSequence(sequence: Int) {
        this.sequence = sequence
    }

    fun updateIdAndSequence(sequence: Int, id: Int) {
        this.sequence = sequence
        this.id = id
    }


}