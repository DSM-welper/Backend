package welper.welper.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class OpenAPIField(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)

        val id: Int=0,
        val apiId: String?,
        val fieldName: String?,
        val fieldContent: String?,
)