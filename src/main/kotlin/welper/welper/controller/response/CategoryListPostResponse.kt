package welper.welper.controller.response

    data class CategoryListPostResponse(
        val toTalPage: Int,
        val servList: List<ServList>,
) {
    class ServList(
            val inqNum: String?,
            val jurMnofNm: String?,
            val jurOrgNm: String?,
            val servDgst: String?,
            val servDtlLink: String?,
            val servId: String?,
            val servNm: String?,
            val svcfrstRegTs: String?,
            val isBookMark:Boolean
    )
    {
        override fun hashCode(): Int {
            return (inqNum.toString().hashCode() + jurMnofNm.toString().hashCode() +
                    jurOrgNm.toString().hashCode() + servDgst.toString().hashCode()
                    + servDtlLink.toString().hashCode() + servId.toString().hashCode()
                    + servNm.toString().hashCode() + svcfrstRegTs.toString().hashCode()

                    )
        }
//
        override fun equals(other: Any?): Boolean {
            if (other is ServList) {
                return (other.inqNum == this.inqNum
                        && other.jurOrgNm == this.jurOrgNm
                        && other.jurMnofNm == this.jurMnofNm
                        && other.servDgst == this.servDgst
                        && other.servDtlLink == this.servDtlLink
                        && other.servId == this.servId
                        && other.servNm == this.servNm
                        && other.svcfrstRegTs == this.svcfrstRegTs
                        )
            }
            return false
        }
    }
}