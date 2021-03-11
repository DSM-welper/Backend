package welper.welper.domain

import javax.persistence.*

@Entity
@Table("Post")
class Post(
        @Id
        @Column(name = "email")
        val id: Int,

        @Column(name = "title")
        val title: String,

        @Column(name = "content")
        val content: String,

        @Column(name = "createdAt")
        val createdAt: String,

        @Column(name = "writer")
        @ManyToOne
        @JoinColumn(name = "user", referencedColumnName = "email")
        var user: User,

        val Category: String,
) {

}