package welper.welper.domain

import javax.persistence.*

@Entity
class Comments(

        id: Int = 0,

        @Column(name = "parents")
        val parents: Int,

        @Column(name = "depth")
        val depts: Int,
        @Column(name = "comments")
        val comments: String,

        @ManyToOne
        @JoinColumn(name = "user", referencedColumnName = "postId")
        var post: Post,

        @ManyToOne
        @JoinColumn(name = "user", referencedColumnName = "mail")
        var user: User,

        sequence: Int
) {

    @Column(name = "sequence", nullable = false, length = 555)
    var sequence = sequence
        private set

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id = id
        private set

    fun updateSequence(sequence: Int) {
        this.sequence = sequence
    }

    fun updateIdAndSequence(sequence: Int, id: Int) {
        this.sequence = sequence
        this.id = id
    }


}