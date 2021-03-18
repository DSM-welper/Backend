package welper.welper.controller.response

class RandomCategoryResponse(
        val ageList: AgeList,
        val marryList: MarryList,
        val genderList: GenderList,
) {
    class AgeList(
            val type: String = "Age",
            val list: List<DetailRandomList>,
    )

    class MarryList(
            val type: String = "Marry",
            val list: List<DetailRandomList>,
    )

    class GenderList(
            val type: String = "Gender",
            val list: List<DetailRandomList>,
    )

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