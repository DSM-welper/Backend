package welper.welper.domain

import javax.persistence.*

@Entity
@Table(name = "open_api_post")

class OpenApiPost(
        @Id
        val servId: String?, //서비스ID
        val servNm: String?, //서비스명
        val inqNum: String?, //조회수
        val jurMnofNm: String?, //소관부처명
        val jurOrgNm: String?, //소관조직명
        val servDgst: String, //서비스 요약
        val servDtlLink: String?, //서비스 상세 링크
        val svcfrstRegTs: String?,//서비스 등록일
)