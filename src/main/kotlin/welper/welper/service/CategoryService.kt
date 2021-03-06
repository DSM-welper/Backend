package welper.welper.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import welper.welper.controller.response.CategoryDetailResponse
import welper.welper.controller.response.CategoryListPostResponse
import welper.welper.controller.response.RandomCategoryResponse
import welper.welper.domain.BookMark
import welper.welper.domain.OpenApICategory
import welper.welper.domain.User
import welper.welper.domain.attribute.*
import welper.welper.exception.*
import welper.welper.repository.BookMarkRepository
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
        private val bookMarkRepository: BookMarkRepository,
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
        if (marry == "??????") {
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
        val list: MutableList<RandomCategoryResponse.DetailRandomList> = mutableListOf()
        list.addAll(getDetailRandomArray(list, lifeList))
        list.addAll(getDetailRandomArray(list, marryList))
        list.addAll(getDetailRandomArray(list, genderList))
        return RandomCategoryResponse(
                recommendList = list
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

        val list: MutableList<RandomCategoryResponse.DetailRandomList> = mutableListOf()
        list.addAll(getDetailRandomArray(list, lifeList))
        list.addAll(getDetailRandomArray(list, marryList))
        list.addAll(getDetailRandomArray(list, genderList))
        return RandomCategoryResponse(
                recommendList = list
        )
    }

    fun showCategoryTagList(categoryNameList: List<Category>, numOfPage: Int, token: String?)
            : CategoryListPostResponse {
        val isValid: Boolean = if (token != null) {
            jwtService.isValid(token)
        } else {
            false
        }
        val list = categoryNameList.map { it.value }.filter { it != "??????" }
        val categoryList: MutableList<OpenApICategory> =
                openApiCategoryRepository.findSeveralByCategory(list)
        val servList: List<CategoryListPostResponse.ServList>
        if (isValid && token != null) {
            val email: String = jwtService.getEmail(token)
            servList = categoryList.map {
                CategoryListPostResponse.ServList(
                        servDgst = it.openApiPost.servDgst,
                        servDtlLink = it.openApiPost.servDtlLink,
                        servId = it.openApiPost.servId,
                        servNm = it.openApiPost.servNm,
                        inqNum = it.openApiPost.inqNum,
                        jurMnofNm = it.openApiPost.jurMnofNm,
                        jurOrgNm = it.openApiPost.jurOrgNm,
                        svcfrstRegTs = it.openApiPost.svcfrstRegTs,
                        isBookMark = bookMarkRepository.existsByEmailAndOpenApiPost(email, it.openApiPost)
                )
            }
        } else {
            servList = categoryList.map {
                CategoryListPostResponse.ServList(
                        servDgst = it.openApiPost.servDgst,
                        servDtlLink = it.openApiPost.servDtlLink,
                        servId = it.openApiPost.servId,
                        servNm = it.openApiPost.servNm,
                        inqNum = it.openApiPost.inqNum,
                        jurMnofNm = it.openApiPost.jurMnofNm,
                        jurOrgNm = it.openApiPost.jurOrgNm,
                        svcfrstRegTs = it.openApiPost.svcfrstRegTs,
                        isBookMark = false
                )
            }
        }
        val categoryNameCount = list.count()
        val deduplicationServList: MutableList<CategoryListPostResponse.ServList> = servList.groupBy { it.servId }
                .filter { it.value.size == categoryNameCount }.flatMap { it.value }.toHashSet().toMutableList()

        val lastServList: MutableList<CategoryListPostResponse.ServList> =
                getPageOfList(numOfPage, deduplicationServList)

        return CategoryListPostResponse(
                servList = lastServList,
                toTalPage = deduplicationServList.size / 10 + 1,
        )
    }

    fun showDetailCategory(id: String): CategoryDetailResponse {
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

    fun showSearchCategory(content: String, numOfPage: Int, token: String?): CategoryListPostResponse {
        val isValid: Boolean = if (token != null) {
            jwtService.isValid(token)
        } else {
            false
        }
        val list = openApiPostRepository.findAll()
        val servList: MutableList<CategoryListPostResponse.ServList> = mutableListOf()
        if (isValid && token != null) {
            val email: String = jwtService.getEmail(token)
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
                                isBookMark = bookMarkRepository.existsByEmailAndOpenApiPost(email, it)
                        )
                )
            }
        } else {
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
                                isBookMark = false,
                        )
                )
            }
        }


        val lastServList: MutableList<CategoryListPostResponse.ServList> =
                getPageOfList(numOfPage, servList)

        return CategoryListPostResponse(
                servList = lastServList,
                toTalPage = servList.size / 10 + 1,
        )
    }

    fun showCategoryList(numOfPage: Int, token: String?): CategoryListPostResponse {
        val isValid: Boolean = if (token != null) {
            jwtService.isValid(token)
        } else {
            false
        }
        val list = openApiPostRepository.findAll()
        val servList: MutableList<CategoryListPostResponse.ServList> = mutableListOf()
        if (isValid && token != null) {
            val email: String = jwtService.getEmail(token)
            list.forEach {
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
                                isBookMark = bookMarkRepository.existsByEmailAndOpenApiPost(email, it)
                        ))
            }
        } else {
            list.forEach {
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
                                isBookMark = false,
                        ))
            }
        }
        val lastServList: MutableList<CategoryListPostResponse.ServList> =
                getPageOfList(numOfPage, servList as MutableList<CategoryListPostResponse.ServList>)

        return CategoryListPostResponse(
                servList = lastServList,
                toTalPage = servList.size / 10 + 1,

                )
    }

    fun bookMarkCategory(token: String, servId: String) {
        val email: String = jwtService.getEmail(token)
        userRepository.findByEmail(email) ?: throw UserNotFoundException(email)
        val openApiPost = openApiPostRepository.findByIdOrNull(servId) ?: throw CategoryNotFoundException(servId)
        if (!bookMarkRepository.existsByEmailAndOpenApiPost(email, openApiPost)) {
            bookMarkRepository.save(
                    BookMark(
                            email = email, openApiPost = openApiPost
                    )
            )
        }
    }

    fun bookMarkDeleteCategory(token: String, servId: String) {
        val email: String = jwtService.getEmail(token)
        userRepository.findByEmail(email) ?: throw UserNotFoundException(email)
        val openApiPost = openApiPostRepository.findByIdOrNull(servId) ?: throw CategoryNotFoundException(servId)
        if (bookMarkRepository.existsByEmailAndOpenApiPost(email, openApiPost)) {
            bookMarkRepository.delete(
                    bookMarkRepository.findByEmailAndOpenApiPost(email, openApiPost)
            )
        } else {
            throw CategoryNotFound(servId)
        }
    }

    private fun getPageOfList(numOfPage: Int, moreDeduplicationServList: MutableList<CategoryListPostResponse.ServList>):
            MutableList<CategoryListPostResponse.ServList> {
        val numOfServList: Int = numOfPage * 10;
        val lastServList: MutableList<CategoryListPostResponse.ServList> = mutableListOf();
        if (moreDeduplicationServList.size < numOfServList + 1)
            throw NonNumOfPageOutOfBoundsException()
        val num = moreDeduplicationServList.size - numOfServList
        if (num >= 10)
            for (i in numOfServList until (numOfServList + 10)) {
                lastServList.add(moreDeduplicationServList[i])
            }
        else
            for (i in numOfServList until (numOfServList + num)) {
                lastServList.add(moreDeduplicationServList[i])
            }

        return lastServList
    }

    private fun getGender(gender: Gender): String {
        return when (gender) {
            Gender.MEN -> "????????????"
            Gender.SECRET -> "????????????"
            Gender.WOMEN -> "??????"
        }
    }

    private fun getAge(age: Int): String {
        return when (age) {
            in 0..6 -> "?????????"
            in 7..12 -> "??????"
            in 13..19 -> "?????????"
            in 19..40 -> "??????"
            in 41..65 -> "?????????"
            else -> "??????"
        }
    }

    private fun getMarry(marry: Marry): String {
        return when (marry) {
            Marry.DO -> "????????????"
            Marry.DONOT -> "????????????"
            Marry.SECRET -> "??????"
        }
    }

    private fun getDetailRandomArray(
            beforeList: MutableList<RandomCategoryResponse.DetailRandomList>?,
            list: MutableSet<OpenApICategory>,
    ): List<RandomCategoryResponse.DetailRandomList> {
        val detailRandomList: MutableList<RandomCategoryResponse.DetailRandomList> = mutableListOf()

        if (list.isNotEmpty()) {
            var filterList: MutableList<OpenApICategory> = mutableListOf()
            filterList = if (!beforeList.isNullOrEmpty()) {
                list.filter {
                    it.openApiPost.inqNum != beforeList[0].inqNum &&
                            it.openApiPost.inqNum != beforeList[1].inqNum
                } as MutableList<OpenApICategory>
            } else list.toMutableList()

            var num = 1;
            if (list.size < 2)
                num = list.size - 1
            for (i in 0..num) {
                val lifeArrayList: OpenApICategory = filterList.random()
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
                filterList.remove(lifeArrayList)
            }
        }
        return detailRandomList
    }

    private fun createDetailList(doc: Document, targetName: String)
            : List<CategoryDetailResponse.DetailList>? {
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
        return if (list.isNotEmpty())
            list
        else null
    }

    private fun getTagValue(tag: String, eElement: Element): String? {
        val nlList: NodeList = eElement.getElementsByTagName(tag).item(0).childNodes ?: return "a"
        val nValue: Node = nlList.item(0) ?: return "a"
        return nValue.nodeValue
    }

}