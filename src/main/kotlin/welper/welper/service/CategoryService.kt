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
import welper.welper.domain.OpenApICategory
import welper.welper.domain.OpenApiPost
import welper.welper.domain.attribute.Category
import welper.welper.domain.attribute.DesireArray
import welper.welper.domain.attribute.LifeArray
import welper.welper.domain.attribute.TrgterindvdlArray
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

    fun getCategory(categoryNameList: List<Category>)
            : CategoryListPostResponse {
        val list: MutableList<String> = mutableListOf()
        categoryNameList.forEach {
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
        println(servList.size)
        val set: List<CategoryListPostResponse.ServList> = servList.groupBy { it.servId }
                .filter { it.value.size == categoryNameList.count() }.flatMap { it.value }


        return CategoryListPostResponse(
                servList = set
        )
    }

    fun detailCategory(id: String): CategoryDetailResponse {
        val urlstr = "http://www.bokjiro.go.kr/openapi/rest/gvmtWelSvc"
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
                applmetList = createApplmetList(doc),
                basfrmList = createBasfrmList(doc),
                baslawList = createBaslawList(doc),
                inqplCtadrList = createInqplCtarList(doc),
                servDgst = getTagValue("servDgst", eElement),
                servId = getTagValue("servId", eElement),
                servNm = getTagValue("servNm", eElement),
                slctCritCn = getTagValue("slctCritCn", eElement),
                tgtrDtlCn = getTagValue("tgtrDtlCn", eElement),
        )
    }

    private fun categoryURL(
            num: Int,
            list: MutableList<Document>, searchWrd: String,
    )
            : MutableList<Document> {
        println("시작$num")
        var num2 = num
        val urlstr = "http://www.bokjiro.go.kr/openapi/rest/gvmtWelSvc" +
                "?crtiKey=" +
                "keTuCooJ8R9Ao5LERVj48XiH87g5hLr3teCu06S8KTfHxSwtGkz0nAS%2BYS8v35JrIJ%2FxYDe3%2BtshuX2%2B2EZg3w%3D%3D" +
                "&callTp=L" +
                "&pageNo=$num2" +
                "&numOfRows=100$searchWrd"
        val dbFactoty: DocumentBuilderFactory = DocumentBuilderFactory.newInstance();
        val dBuilder: DocumentBuilder = dbFactoty.newDocumentBuilder();
        val doc: Document = dBuilder.parse(urlstr)
        println(doc.getElementsByTagName("servList").length)
        if (doc.getElementsByTagName("servList").length == 100) {
            num2++
            categoryURL(num2, list, searchWrd)
        }
        list.add(doc)

        return list
    }

    private fun createBaslawList(doc: Document): List<CategoryDetailResponse.BaslawList> {
        val nList: NodeList = doc.getElementsByTagName("baslawList");
        val list: ArrayList<CategoryDetailResponse.BaslawList> = ArrayList()

        println("size:${nList.length}")
        for (i in 0 until nList.length) {
            val nNode: Node = nList.item(i)
            val eElement = nNode as Element
            val a = CategoryDetailResponse.BaslawList(
                    servSeCode = getTagValue("servSeCode", eElement),
                    servSeDetailNm = getTagValue("servSeDetailNm", eElement),
                    servSeDetailLink = getTagValue("servSeDetailNm", eElement)
            )
            list.add(a)
        }
        return list
    }

    private fun createBasfrmList(doc: Document): List<CategoryDetailResponse.BasfrmList> {
        val nList: NodeList = doc.getElementsByTagName("basfrmList");
        val list: ArrayList<CategoryDetailResponse.BasfrmList> = ArrayList()
        println("size:${nList.length}")

        for (i in 0 until nList.length) {
            val nNode: Node = nList.item(i)
            val eElement = nNode as Element
            val a = CategoryDetailResponse.BasfrmList(
                    servSeCode = getTagValue("servSeCode", eElement),
                    servSeDetailNm = getTagValue("servSeDetailNm", eElement),
                    servSeDetailLink = getTagValue("servSeDetailLink", eElement)
            )
            list.add(a)
        }
        return list
    }

    private fun createInqplCtarList(doc: Document): List<CategoryDetailResponse.InqplCtadrList> {
        val nList: NodeList = doc.getElementsByTagName("inqplCtadrList");

        val list: ArrayList<CategoryDetailResponse.InqplCtadrList> = ArrayList()
        for (i in 0 until nList.length) {
            val nNode: Node = nList.item(i)
            val eElement = nNode as Element
            val a = CategoryDetailResponse.InqplCtadrList(
                    servSeCode = getTagValue("servSeCode", eElement),
                    servSeDetailNm = getTagValue("servSeDetailNm", eElement),
                    servSeDetailLink = getTagValue("servSeDetailLink", eElement)
            )
            list.add(a)
        }
        return list
    }

    private fun createApplmetList(doc: Document): List<CategoryDetailResponse.ApplmetList> {
        val nList: NodeList = doc.getElementsByTagName("applmetList");
        val list: ArrayList<CategoryDetailResponse.ApplmetList> = ArrayList()
        for (i in 0 until nList.length) {
            val nNode: Node = nList.item(i)
            val eElement = nNode as Element
            val a = CategoryDetailResponse.ApplmetList(
                    servSeCode = getTagValue("servSeCode", eElement),
                    servSeDetailNm = getTagValue("servSeDetailNm", eElement),
                    servSeDetailLink = getTagValue("servSeDetailLink", eElement)
            )
            list.add(a)
        }
        return list
    }

    private fun createServList(doc: Document): List<CategoryListPostResponse.ServList> {

        val nList: NodeList = doc.getElementsByTagName("servList");
        println("size:${nList.length}")

        val list: ArrayList<CategoryListPostResponse.ServList> = ArrayList()
        for (i in 0 until nList.length) {
            val nNode: Node = nList.item(i)
            val eElement = nNode as Element
            val a = CategoryListPostResponse.ServList(
                    inqNum = getTagValue("inqNum", eElement),
                    jurOrgNm = getTagValue("jurOrgNm", eElement),
                    servDgst = getTagValue("servDgst", eElement),
                    servDtlLink = getTagValue("servDtlLink", eElement),
                    servId = getTagValue("servId", eElement),
                    servNm = getTagValue("servNm", eElement),
                    svcfrstRegTs = getTagValue("svcfrstRegTs", eElement),
                    jurMnofNm = getTagValue("jurMnofNm", eElement),
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


    fun categorySearch(content: String): CategoryListPostResponse {
        val list: MutableList<Document> = mutableListOf()
        val docList: MutableList<Document> = categoryURL(1, list, content)
        val servList: MutableList<CategoryListPostResponse.ServList> = mutableListOf()
        docList.forEach {
            servList.addAll(createServList(it))
        }
        return CategoryListPostResponse(
                servList = servList
        )
    }
}