package welper.welper.controller.response

data class CategoryDetailResponse(
        var wantedDtl: String?,
        var alwServCn: String?,
        val applmetList: List<ApplmetList>,
        val basfrmList: List<BasfrmList>,
        val baslawList: List<BaslawList>,
        val inqplCtadrList:List<InqplCtadrList>,
        val servDgst: String?,
        val servId: String?,
        val servNm: String?,
        val slctCritCn: String?,
        val tgtrDtlCn: String?,
) {
    class ApplmetList(
            val servSeCode: String?,
            val servSeDetailNm: String?,
            val servSeDetailLink: String?,
    )

    class BasfrmList(
            val servSeCode: String?,
            val servSeDetailNm: String?,
            val servSeDetailLink: String?,
    )

    class BaslawList(
            val servSeCode: String?,
            val servSeDetailNm: String?,
            val servSeDetailLink: String?,
    )

    class InqplCtadrList(
            val servSeCode: String?,
            val servSeDetailNm: String?,
            val servSeDetailLink: String?,
    )
}