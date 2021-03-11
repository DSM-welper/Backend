package welper.welper.controller.response

data class CategoryListPostResponse(
        var wantedList:String,
//        val numOfRows:String,
//        val pageNo:Int,
//        val resultCode:String,
//        val resultMessage:String,
        val servList:List<ServList>,
){
    class ServList(
            val inqNUm: String?,
            val jurMnofNm:String?,
            val jurOrgNm:String?,
            val servDgst:String?,
            val servDtlLink:String?,
            val servId:String?,
            val servNm:String?,
            val svcfrstRegTs:String?,
    )
}