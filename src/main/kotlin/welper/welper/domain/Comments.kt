package welper.welper.domain

import javax.persistence.*

@Entity
class Comments (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Int,

    @Column(name ="parents")
    val parents: Int,

    @Column(name = "depth")
    val depts:Int,
    @Column(name = "sequence")
    val sequence:String,

    val comments:String,
    
    @ManyToOne
    @JoinColumn(name = "Post",referencedColumnName = "email")
    val post:Post,
)