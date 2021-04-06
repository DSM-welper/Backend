package welper.welper.domain

import javax.persistence.*

@Entity
@Table(name = "open_api_category")
class OpenApICategory(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int = 0,

        val categoryName: String,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "postId")
        val openApiPost: OpenApiPost,

        )