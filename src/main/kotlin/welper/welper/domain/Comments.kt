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
        @Column(name = "sequence")
        val sequence: Int,
        @Column(name = "comments")
        val comments: String,
        @Column(name = "postId")
        val postId: Int,
)