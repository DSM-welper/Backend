package welper.welper.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import welper.welper.controller.response.CategoryDetailResponse
import welper.welper.controller.response.CategoryListPostResponse
import welper.welper.controller.response.RandomCategoryResponse
import welper.welper.domain.OpenApICategory
import welper.welper.domain.User
import welper.welper.domain.attribute.*
import welper.welper.exception.NonExistCategoryDetailException
import welper.welper.exception.UserNotFoundException
import welper.welper.repository.OpenApiCategoryRepository
import welper.welper.repository.OpenApiPostRepository
import welper.welper.repository.UserRepository
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory

@Service
class CategoryService(
        @Value("\${API_KEY}")
        private val key: String,
        private val openApiCategoryRepository: OpenApiCategoryRepository,
        private val openApiPostRepository: OpenApiPostRepository,
        private val jwtService: JwtService,
        private val userRepository: UserRepository,
) {
    fun userCategory(token: String): RandomCategoryResponse {
        val email = jwtService.getEmail(token)
        val user: User = userRepository.findByEmail(email) ?: throw UserNotFoundException(email)
        val lifeList: MutableSet<OpenApICategory> = mutableSetOf()
        val genderList: MutableSet<OpenApICategory> = mutableSetOf()
        val marryList: MutableSet<OpenApICategory> = mutableSetOf()
        val gender: String = getGender(user.gender)
        val age: String = getAge(user.age)
        val marry: String = getMarry(user.marry)
        lifeList.addAll(
                openApiCategoryRepository.findAllByCategoryName(age)
        )
        genderList.addAll(
                openApiCategoryRepository.findAllByCategoryName(gender)
        )
        if (marry == "모두") {
            DesireArray.values().forEach {
                marryList.addAll(
                        openApiCategoryRepository.findAllByCategoryName(it.value)
                )
            }
            openApiCategoryRepository.findAll()
        } else
            marryList.addAll(
                    openApiCategoryRepository.findAllByCategoryName(marry)
            )
        return RandomCategoryResponse(
                ageList = RandomCategoryResponse.AgeList(
                        list = getDetailRandomArray(lifeList)),
                genderList = RandomCategoryResponse.GenderList(
                        list = getDetailRandomArray(genderList)),
                marryList = RandomCategoryResponse.MarryList(
                        list = getDetailRandomArray(marryList)),
        )
    }

    fun randomCategory(): RandomCategoryResponse {

        val lifeList: MutableSet<OpenApICategory> = mutableSetOf()
        val genderList: MutableSet<OpenApICategory> = mutableSetOf()
        val marryList: MutableSet<OpenApICategory> = mutableSetOf()

        LifeArray.values().forEach {
            lifeList.addAll(
                    openApiCategoryRepository.findAllByCategoryName(it.value)
            )
        }
        MarryArray.values().forEach {
            marryList.addAll(
                    openApiCategoryRepository.findAllByCategoryName(it.value)
            )
        }
        GenderArray.values().forEach {
            genderList.addAll(
                    openApiCategoryRepository.findAllByCategoryName(it.value)
            )
        }

        return RandomCategoryResponse(
                ageList = RandomCategoryResponse.AgeList(
                        type = "Random",
                        list = getDetailRandomArray(lifeList)),
                genderList = RandomCategoryResponse.GenderList(
                        type = "Random",
                        list = getDetailRandomArray(genderList)),
                marryList = RandomCategoryResponse.MarryList(
                        type = "Random",
                        list = getDetailRandomArray(marryList)),
        )
    }

    fun showCategoryTagList(categoryNameList: List<Category>, numOfPage: Int)
            : CategoryListPostResponse {
        val list = categoryNameList.map { it.value }
        val categoryList: MutableList<OpenApICategory> =
                openApiCategoryRepository.findSeveralByCategory(list)
        val servList = categoryList.map {
            CategoryListPostResponse.ServList(
                    servDgst = it.openApiPost.servDgst,
                    servDtlLink = it.openApiPost.servDtlLink,
                    servId = it.openApiPost.servId,
                    servNm = it.openApiPost.servNm,
                    inqNum = it.openApiPost.inqNum,
                    jurMnofNm = it.openApiPost.jurMnofNm,
                    jurOrgNm = it.openApiPost.jurOrgNm,
                    svcfrstRegTs = it.openApiPost.svcfrstRegTs,
            )
        }
        val categoryNameCount = categoryNameList.count()
        val deduplicationServList: List<CategoryListPostResponse.ServList> = servList.groupBy { it.servId }
                .filter { it.value.size == categoryNameCount }.flatMap { it.value }
        val moreDeduplicationServList: MutableList<CategoryListPostResponse.ServList> = mutableListOf()
        for (i in deduplicationServList.indices step categoryNameCount) {
            moreDeduplicationServList.add(deduplicationServList[i])
        }
        val lastServList: MutableList<CategoryListPostResponse.ServList> =
                getPageOfList(numOfPage, moreDeduplicationServList)

        return CategoryListPostResponse(
                servList = lastServList
        )
    }

    private fun getPageOfList(numOfPage: Int, moreDeduplicationServList: MutableList<CategoryListPostResponse.ServList>):
            MutableList<CategoryListPostResponse.ServList> {
        val numOfServList: Int = numOfPage * 10;
        val lastServList: MutableList<CategoryListPostResponse.ServList> = mutableListOf();
        for (i in numOfServList..(numOfServList + 10)) {
            lastServList.add(moreDeduplicationServList[i])
        }
        return lastServList
    }

    fun detailCategory(id: String): CategoryDetailResponse {
        try {
            val urlstr = "http://www.bokjiro.go.kr/openapi/rest/gvmtWelSvc" +
                    "?crtiKey=$key" +
                    "&callTp=D" +
                    "&servId=$id"
            val dbFactoty: DocumentBuilderFactory = DocumentBuilderFactory.newInstance();
            val dBuilder: DocumentBuilder = dbFactoty.newDocumentBuilder();
            val doc: Document = dBuilder.parse(urlstr)
            val nList: NodeList = doc.getElementsByTagName("wantedDtl")
            val nNode: Node = nList.item(0)
            val eElement = nNode as Element
            return CategoryDetailResponse(
                    alwServCn = getTagValue("alwServCn", eElement),
                    applmetList = createDetailList(doc, "applmetList"),
                    basfrmList = createDetailList(doc, "basfrmList"),
                    baslawList = createDetailList(doc, "baslawList"),
                    inqplCtadrList = createDetailList(doc, "inqplCtadrList"),
                    servDgst = getTagValue("servDgst", eElement),
                    servId = getTagValue("servId", eElement),
                    servNm = getTagValue("servNm", eElement),
                    slctCritCn = getTagValue("slctCritCn", eElement),
                    tgtrDtlCn = getTagValue("tgtrDtlCn", eElement),
            )
        } catch (e: Exception) {
            throw NonExistCategoryDetailException()
        }
    }

    fun showSearchCategory(content: String): CategoryListPostResponse {
        val list = openApiPostRepository.findAll()
        val servList: MutableList<CategoryListPostResponse.ServList> = mutableListOf()
        list.filter {
            it.servDgst.contains(content)
        }.forEach {
            servList.add(
                    CategoryListPostResponse.ServList(
                            inqNum = it.inqNum,
                            jurMnofNm = it.jurMnofNm,
                            jurOrgNm = it.jurOrgNm,
                            servDgst = it.servDgst,
                            servDtlLink = it.servDtlLink,
                            servId = it.servId,
                            servNm = it.servNm,
                            svcfrstRegTs = it.svcfrstRegTs,
                    )
            )
        }
        return CategoryListPostResponse(
                servList = servList
        )
    }

    fun showCategoryList(numOfPage: Int): CategoryListPostResponse {
        val list = openApiPostRepository.findAll()
        val servList = list.map{
                    CategoryListPostResponse.ServList(
                            inqNum = it.inqNum,
                            jurMnofNm = it.jurMnofNm,
                            jurOrgNm = it.jurOrgNm,
                            servDgst = it.servDgst,
                            servDtlLink = it.servDtlLink,
                            servId = it.servId,
                            servNm = it.servNm,
                            svcfrstRegTs = it.svcfrstRegTs,
                    )
        }
        val lastServList: MutableList<CategoryListPostResponse.ServList> =
            getPageOfList(numOfPage, servList as MutableList<CategoryListPostResponse.ServList>)

        return CategoryListPostResponse(
                servList = lastServList
        )
    }

    private fun getGender(gender: Gender): String {
        return when (gender) {
            Gender.MEN -> "특성없음"
            Gender.SECRET -> "특성없음"
            Gender.WOMEN -> "여성"
        }
    }

    private fun getAge(age: Int): String {
        return when (age) {
            in 0..6 -> "영유아"
            in 7..12 -> "아동"
            in 13..19 -> "청소년"
            in 19..40 -> "청년"
            in 41..65 -> "중장년"
            else -> "노년"
        }
    }

    private fun getMarry(marry: Marry): String {
        return when (marry) {
            Marry.DO -> "가족관계"
            Marry.DONOT -> "가구없음"
            Marry.SECRET -> "모두"
        }
    }

    private fun getDetailRandomArray(list: MutableSet<OpenApICategory>): List<RandomCategoryResponse.DetailRandomList> {
        val detailRandomList: MutableList<RandomCategoryResponse.DetailRandomList> = mutableListOf()
        if (list.isNotEmpty()) {
            var num: Int = 2;
            if (list.size < 3)
                num = list.size - 1
            for (i in 0..num) {
                val lifeArrayList: OpenApICategory = list.random()
                detailRandomList.add(
                        RandomCategoryResponse.DetailRandomList(
                                inqNum = lifeArrayList.openApiPost.inqNum,
                                jurMnofNm = lifeArrayList.openApiPost.jurMnofNm,
                                jurOrgNm = lifeArrayList.openApiPost.jurOrgNm,
                                servDgst = lifeArrayList.openApiPost.servDgst,
                                servDtlLink = lifeArrayList.openApiPost.servDtlLink,
                                servId = lifeArrayList.openApiPost.servId,
                                servNm = lifeArrayList.openApiPost.servNm,
                                svcfrstRegTs = lifeArrayList.openApiPost.svcfrstRegTs,
                        )
                )
                list.remove(lifeArrayList)
            }
        }
        return detailRandomList
    }

    private fun createDetailList(doc: Document, targetName: String)
            : List<CategoryDetailResponse.DetailList> {
        val nList: NodeList = doc.getElementsByTagName(targetName)
        val list: MutableList<CategoryDetailResponse.DetailList> = mutableListOf()

        println("size:${nList.length}")
        for (i in 0 until nList.length) {
            val nNode: Node = nList.item(i)
            val eElement = nNode as Element
            val a = CategoryDetailResponse.DetailList(
                    servSeCode = getTagValue("servSeCode", eElement),
                    servSeDetailNm = getTagValue("servSeDetailNm", eElement),
                    servSeDetailLink = getTagValue("servSeDetailLink", eElement)
            )
            list.add(a)
        }
        return list
    }

    private fun getTagValue(tag: String, eElement: Element): String? {
        val nlList: NodeList = eElement.getElementsByTagName(tag).item(0).childNodes ?: return "a"
        val nValue: Node = nlList.item(0) ?: return "a"
        return nValue.nodeValue
    }

}