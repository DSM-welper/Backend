package welper.welper.controller.response

class RandomCategoryResponse(
        val recommendList:List<DetailRandomList>
) {


    class DetailRandomList(
            val inqNum: String?,
            val jurMnofNm: String?,
            val jurOrgNm: String?,
            val servDgst: String?,
            val servDtlLink: String?,
            val servId: String?,
            val servNm: String?,
            val svcfrstRegTs: String?,
    )
}
