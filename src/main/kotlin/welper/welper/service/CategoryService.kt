package welper.welper.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import welper.welper.controller.response.CategoryDetailResponse
import welper.welper.controller.response.CategoryListPostResponse
import welper.welper.domain.attribute.DesireArray
import welper.welper.domain.attribute.LifeArray
import welper.welper.domain.attribute.TrgterindvdlArray
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory

@Service
class CategoryService(
        @Value("\${API_KEY}")
        private val key: String,
) {

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
                wantedDtl = getWantedList(doc),
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

    fun getCategory(lifeArray: LifeArray, trgterindvdlArray: TrgterindvdlArray, desireArray: DesireArray): CategoryListPostResponse {

        val a: String = getLifeArray(lifeArray)
        var string = ""
        if (!a.equals("")) {
            string = "&$a"
        }
        val urlstr = "http://www.bokjiro.go.kr/openapi/rest/gvmtWelSvc" +
                "?crtiKey=$key" +
                "&callTp=L" +
                "&pageNo=1" +
                "&numOfRows=100" +
                string

        val dbFactoty: DocumentBuilderFactory = DocumentBuilderFactory.newInstance();
        val dBuilder: DocumentBuilder = dbFactoty.newDocumentBuilder();
        val doc: Document = dBuilder.parse(urlstr)

        return CategoryListPostResponse(
                wantedList = getWantedList(doc),
                servList = createServList(doc)
        )
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
        println("size:${nList.length}")

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
        println("size:${nList.length}")
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

        val list: ArrayList<CategoryListPostResponse.ServList> = ArrayList()
        for (i in 0 until nList.length) {
            val nNode: Node = nList.item(i)
            val eElement = nNode as Element
            val a = CategoryListPostResponse.ServList(
                    inqNUm = getTagValue("inqNum", eElement),
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

    private fun getWantedList(doc: Document): String {
        return doc.getDocumentElement().getNodeName()
    }

    private fun getTagValue(tag: String, eElement: Element): String? {
        val nlList: NodeList = eElement.getElementsByTagName(tag).item(0).getChildNodes()
        val nValue: Node = nlList.item(0) as Node ?: return null
        return nValue.getNodeValue()
    }

    private fun getLifeArray(lifeArray: LifeArray): String {

        return when (lifeArray) {
            LifeArray.doNot -> ""
            LifeArray.child -> "002"
            LifeArray.infants -> "001"
            LifeArray.middleAge -> "005"
            LifeArray.oldAge -> "006"
            LifeArray.teenage -> "003"
            LifeArray.youth -> "004"
        }
    }
}