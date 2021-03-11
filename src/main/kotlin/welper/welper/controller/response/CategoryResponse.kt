package welper.welper.controller.response


data class CategoryResponse(
        val wantedList: String,
        val numOfRows: Int,
        val pageNo: Int,
        val resultCode: String,
        val resultMessage: String,
        val servList: List<ServList>,
) {
    data class ServList(
        val inqNum:Int,
        val jurOrgNm:String,
        val servDgst:String,
        val servDtlLink:String,
        val servId:String,
        val servNm:String,
        val svcfrstRegTs:String
    )
}
