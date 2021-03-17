package welper.welper.controller.response

class RandomCategoryResponse(
        val ageList: AgeList,
        val marryList: MarryList,
        val genderList: GenderList,
) {
    class AgeList(
            val type: String = "Age",
            val list: List<DetailList>,
    )

    class MarryList(
            val type: String = "Marry",
            val list: List<DetailList>,
    )

    class GenderList(
            val type: String = "Gender",
            val list: List<DetailList>,
    )

    class DetailList(
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
