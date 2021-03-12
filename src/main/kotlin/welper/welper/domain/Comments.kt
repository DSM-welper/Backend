package welper.welper.domain

import javax.persistence.*

@Entity
class Comments (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sequence")
    val sequence:Int,

    @Column(name ="parents")
    val parents: Int,

    @Column(name = "depth")
    val depts:Int,

    @ManyToOne
    @JoinColumn(name = "Post",referencedColumnName = "email")
    val post:Post,
)