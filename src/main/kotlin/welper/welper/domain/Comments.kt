package welper.welper.domain

import javax.persistence.*

@Entity
class Comments(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int = 0,

        @Column(name = "parents")
        val parents: Int,

        @Column(name = "depth")
        val depts: Int,
        @Column(name = "comments")
        val comments: String,
        @Column(name = "postId")
        val postId: Int,
        sequence: Int,

        ) {

    @Column(name = "sequence", nullable = false, length = 555)
    var sequence = sequence
        private set

    fun updateSequence(sequence: Int) {
        this.sequence = sequence
    }
}