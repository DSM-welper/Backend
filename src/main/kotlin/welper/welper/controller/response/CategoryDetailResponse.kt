package welper.welper.controller.response

data class CategoryDetailResponse(
        var alwServCn: String?,
        val applmetList: List<DetailList>? ,
        val basfrmList: List<DetailList>?,
        val baslawList: List<DetailList>?,
        val inqplCtadrList: List<DetailList>?,
        val servDgst: String?,
        val servId: String?,
        val servNm: String?,
        val slctCritCn: String?,
        val tgtrDtlCn: String?,
) {
    class DetailList(
            val servSeCode: String?,
            val servSeDetailNm: String?,
            val servSeDetailLink: String?,
    )
}