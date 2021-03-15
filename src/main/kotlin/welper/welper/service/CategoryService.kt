package welper.welper.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import welper.welper.controller.response.CategoryDetailResponse
import welper.welper.controller.response.CategoryListPostResponse
import welper.welper.controller.response.PostListResponse
import welper.welper.domain.attribute.DesireArray
import welper.welper.domain.attribute.LifeArray
import welper.welper.domain.attribute.TrgterindvdlArray
import javax.print.Doc
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

    fun getCategory(lifeArray: LifeArray, desireArray: DesireArray, trgterindvdlArray: TrgterindvdlArray)
            : CategoryListPostResponse {
        println("시작")
        val life: String = getLifeArray(lifeArray)
        val desire: String = getDesireArray(desireArray)
        val trgterindvdl: String = getTrgterindvdlArray(trgterindvdlArray)

        var trgterindvded = ""
        var lifeArrayed = ""
        var desireArrayed = ""
        if (life != "") {
            lifeArrayed = "&lifeArray=$life"
        }
        if (desire != "") {
            desireArrayed = "&desireArray=$desire"
        }
        if (trgterindvdl != "") {
            trgterindvded = "&trgterindvdl=$trgterindvdl"
        }
        println("시작2")
        val list: MutableList<Document> = mutableListOf()
        val docList: MutableList<Document> = CategoryURL(1, lifeArrayed, trgterindvded, desireArrayed,list)
        val servList: MutableList<CategoryListPostResponse.ServList> = mutableListOf()

        docList.forEach {
            servList.addAll(createServList(it))
        }
        return CategoryListPostResponse(
                servList = servList
        )
    }

    private fun CategoryURL(num: Int, lifeArrayed: String, trgterindvded: String, desireArrayed: String,list: MutableList<Document>)
            : MutableList<Document> {
        println("시작$num")
        var num2 = num
        val urlstr = "http://www.bokjiro.go.kr/openapi/rest/gvmtWelSvc" +
                "?crtiKey=" +
                "keTuCooJ8R9Ao5LERVj48XiH87g5hLr3teCu06S8KTfHxSwtGkz0nAS%2BYS8v35JrIJ%2FxYDe3%2BtshuX2%2B2EZg3w%3D%3D" +
                "&callTp=L" +
                "&pageNo=$num2" +
                "&numOfRows=100$lifeArrayed$trgterindvded$desireArrayed"
        val dbFactoty: DocumentBuilderFactory = DocumentBuilderFactory.newInstance();
        val dBuilder: DocumentBuilder = dbFactoty.newDocumentBuilder();
        val doc: Document = dBuilder.parse(urlstr)
        println(doc.getElementsByTagName("servList").length)
        list.add(doc)
        if (doc.getElementsByTagName("servList").length == 100) {
            num2++
            CategoryURL(num2, lifeArrayed, trgterindvded, desireArrayed,list)
        }
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
        return doc.documentElement.nodeName
    }

    private fun getTagValue(tag: String, eElement: Element): String? {
        val nlList: NodeList = eElement.getElementsByTagName(tag).item(0).childNodes ?: return "a"
        val nValue: Node = nlList.item(0) ?: return "a"
        return nValue.nodeValue
    }

    private fun getLifeArray(lifeArray: LifeArray): String {
        return when (lifeArray) {
            LifeArray.DONOT -> ""
            LifeArray.CHILD -> "002"
            LifeArray.INFANTS -> "001"
            LifeArray.MIDDLEAGE -> "005"
            LifeArray.OLDAGE -> "006"
            LifeArray.TEENAGE -> "003"
            LifeArray.YOUTH -> "004"

        }
    }

    private fun getDesireArray(desireArray: DesireArray): String {
        return when (desireArray) {
            DesireArray.DONOT -> ""
            DesireArray.SAFETY -> "0000000"
            DesireArray.HEALTH -> "1000000"
            DesireArray.DAILYLIFE -> "2000000"
            DesireArray.FAMILY -> "3000000"
            DesireArray.SOCIAL -> "4000000"
            DesireArray.ECONOMIC -> "5000000"
            DesireArray.EDUCATION -> "6000000"
            DesireArray.EMPLOYMENT -> "7000000"
            DesireArray.LIFE -> "8000000"
            DesireArray.LAW -> "9000000"
            DesireArray.EXCEPT -> "A000000"
        }
    }


    private fun getTrgterindvdlArray(trgterindvdlArray: TrgterindvdlArray)
            : String {
        return when (trgterindvdlArray) {
            TrgterindvdlArray.DONOT -> ""
            TrgterindvdlArray.EXCEPT -> "001"
            TrgterindvdlArray.SINGLEPARENTS -> "002"
            TrgterindvdlArray.MULTICULTURAL -> "003"
            TrgterindvdlArray.GRANDCHILDREN -> "004"
            TrgterindvdlArray.SETTERMIN -> "005"
            TrgterindvdlArray.CHILDHEAD -> "006"
            TrgterindvdlArray.SOLOOLD -> "007"
        }
    }
}