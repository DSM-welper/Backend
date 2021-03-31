package welper.welper.controller.response

data class CategoryListPostResponse(
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
    )
//    {
//        override fun hashCode(): Int {
//            return (inqNum.toString().hashCode() + jurMnofNm.toString().hashCode() +
//                    jurOrgNm.toString().hashCode() + servDgst.toString().hashCode()
//                    + servDtlLink.toString().hashCode() + servId.toString().hashCode()
//                    + servNm.toString().hashCode() + svcfrstRegTs.toString().hashCode()
//
//                    )
//        }
//
//        override fun equals(other: Any?): Boolean {
//            if (other is ServList) {
//                val temp = other
//                return (temp.inqNum == this.inqNum
//                        && temp.jurOrgNm == this.jurOrgNm
//                        && temp.jurMnofNm == this.jurMnofNm
//                        && temp.servDgst == this.servDgst
//                        && temp.servDtlLink == this.servDtlLink
//                        && temp.servId == this.servId
//                        && temp.servNm == this.servNm
//                        && temp.svcfrstRegTs == this.svcfrstRegTs
//                        )
//            }
//            return false
//        }
//    }
}