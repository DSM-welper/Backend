package welper.welper.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity

class OpenAPI(
        @Id
//        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val servId:String?,
        val inqNUm: String?,
        val jurMnofNm:String?,
        val jurOrgNm:String?,
        val servDgst:String?,
        val servDtlLink:String?,
        val servNm:String?,
        val svcfrstRegTs:String?,
)