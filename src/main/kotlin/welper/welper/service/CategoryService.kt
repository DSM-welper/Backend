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
import welper.welper.domain.attribute.Category
import welper.welper.domain.attribute.MarryArray
import welper.welper.domain.attribute.GenderArray
import welper.welper.domain.attribute.LifeArray
import welper.welper.repository.OpenApiCategoryRepository
import welper.welper.repository.OpenApiPostRepository
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory

@Service
class CategoryService(
        @Value("\${API_KEY}")
        private val key: String,
        private val openApiCategoryRepository: OpenApiCategoryRepository,
        private val openApiPostRepository: OpenApiPostRepository,
) {

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

    fun getCategory(categoryNameList: List<Category>)
            : CategoryListPostResponse {
        val list: MutableList<String> = mutableListOf()
        categoryNameList.map {
            list.add(it.value)
        }
        val categoryList: MutableList<OpenApICategory> =
                openApiCategoryRepository.findByCategoryName3(list)
        val servList: MutableList<CategoryListPostResponse.ServList> = mutableListOf()

        categoryList.forEach {
            servList.add(
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
            )
        }
        val count = categoryNameList.count()
        val set: List<CategoryListPostResponse.ServList> = servList.groupBy { it.servId }
                .filter { it.value.size == count }.flatMap { it.value }

        val set2: MutableList<CategoryListPostResponse.ServList> = mutableListOf()
        for (i in set.indices step count) {
            set2.add(set[i])
        }
        return CategoryListPostResponse(
                servList = set2
        )
    }

    fun detailCategory(id: String): CategoryDetailResponse {
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
    }

    fun categorySearch(content: String): CategoryListPostResponse {
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

    fun getAllCategory(): CategoryListPostResponse {
        val list = openApiPostRepository.findAll()
        val servList: MutableList<CategoryListPostResponse.ServList> = mutableListOf()
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
                    )
            )
        }
        return CategoryListPostResponse(
                servList = servList
        )
    }

    private fun getDetailRandomArray(list: MutableSet<OpenApICategory>): List<RandomCategoryResponse.DetailRandomList> {
        val detailRandomList: MutableList<RandomCategoryResponse.DetailRandomList> = mutableListOf()
        for (i in 0..2) {
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