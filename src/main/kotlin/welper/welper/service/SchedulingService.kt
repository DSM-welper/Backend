package welper.welper.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import welper.welper.controller.response.CategoryListPostResponse
import welper.welper.domain.OpenAPI
import welper.welper.domain.OpenAPIField
import welper.welper.domain.attribute.LifeArray
import welper.welper.repository.OpenAPIFieldRepository
import welper.welper.repository.OpenAPIRepository
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory

@Component
class SchedulingService(
        private val openAPIRepository: OpenAPIRepository,
        private val openAPIFieldRepository: OpenAPIFieldRepository,
) {

    @Scheduled(fixedRate = 10000000)
    fun createServList() {
        val list: MutableList<Document> = mutableListOf()
        val docList: MutableList<Document> = categoryURL(1, list)
        val lifeInfants: MutableList<Document> = getLifeArray(1, list, "001")
        val lifeChild: MutableList<Document> = getLifeArray(1, list, "002")
        val lifeTeenAge: MutableList<Document> = getLifeArray(1, list, "003")
        val lifeYouth: MutableList<Document> = getLifeArray(1, list, "004")
        val lifeMiddleAge: MutableList<Document> = getLifeArray(1, list, "005")
        val lifeOldAge: MutableList<Document> = getLifeArray(1, list, "006")

        getCategory(lifeInfants, "lifeArray", "infants")
        getCategory(lifeChild, "lifeArray", "child")
        getCategory(lifeTeenAge, "lifeArray", "teenAge")
        getCategory(lifeYouth, "lifeArray", "youth")
        getCategory(lifeMiddleAge, "lifeArray", "middleAge")
        getCategory(lifeOldAge, "lifeArray", "oldAge")
        getAllCategory(docList)
    }

    private fun getAllCategory(docList: MutableList<Document>) {
        docList.forEach {
            val nList: NodeList = it.getElementsByTagName("servList");

            for (i in 0 until nList.length) {
                val nNode: Node = nList.item(i)
                val eElement = nNode as Element
                val id:String =  getTagValue("servId", eElement)
                if(!openAPIRepository.existsById(id))
                openAPIRepository.save(OpenAPI(

                        inqNUm = getTagValue("inqNum", eElement),
                        jurOrgNm = getTagValue("jurOrgNm", eElement),
                        servDgst = getTagValue("servDgst", eElement),
                        servDtlLink = getTagValue("servDtlLink", eElement),
                        servId = getTagValue("servId", eElement),
                        servNm = getTagValue("servNm", eElement),
                        svcfrstRegTs = getTagValue("svcfrstRegTs", eElement),
                        jurMnofNm = getTagValue("jurMnofNm", eElement),
                )
                )
            }
        }
    }

    private fun getCategory(docList: MutableList<Document>, fieldName: String, fieldContent: String) {
        docList.forEach {
            val nList: NodeList = it.getElementsByTagName("servList");

            for (i in 0 until nList.length) {
                val nNode: Node = nList.item(i)
                val eElement = nNode as Element
                val id: String = getTagValue("servId", eElement)?:""
                if (openAPIFieldRepository.existsByApiIdAndFieldContent(id, fieldContent) == false) {
                    openAPIFieldRepository.save(OpenAPIField(
                            apiId = getTagValue("servId", eElement),
                            fieldName = fieldName,
                            fieldContent = fieldContent,
                    )
                    )
                }
            }
        }
    }


    private fun getTagValue(tag: String, eElement: Element): String {
        val nlList: NodeList = eElement.getElementsByTagName(tag).item(0).childNodes ?: return "a"
        val nValue: Node = nlList.item(0) ?: return "a"
        return nValue.nodeValue
    }

    private fun categoryURL(
            num: Int,
            list: MutableList<Document>,
    )
            : MutableList<Document> {
        println("시작$num")
        var num2 = num
        val urlstr = "http://www.bokjiro.go.kr/openapi/rest/gvmtWelSvc" +
                "?crtiKey=" +
                "keTuCooJ8R9Ao5LERVj48XiH87g5hLr3teCu06S8KTfHxSwtGkz0nAS%2BYS8v35JrIJ%2FxYDe3%2BtshuX2%2B2EZg3w%3D%3D" +
                "&callTp=L" +
                "&pageNo=$num2" +
                "&numOfRows=100"
        val dbFactoty: DocumentBuilderFactory = DocumentBuilderFactory.newInstance();
        val dBuilder: DocumentBuilder = dbFactoty.newDocumentBuilder();
        val doc: Document = dBuilder.parse(urlstr)
        println(doc.getElementsByTagName("servList").length)
        list.add(doc)
        if (doc.getElementsByTagName("servList").length == 100) {
            num2++
            categoryURL(num2, list)
        }
        return list
    }

    private fun getLifeArray(
            num: Int,
            list: MutableList<Document>,
            lifeArray: String,
    )
            : MutableList<Document> {
        var num2 = num
        val urlstr = "http://www.bokjiro.go.kr/openapi/rest/gvmtWelSvc" +
                "?crtiKey=" +
                "keTuCooJ8R9Ao5LERVj48XiH87g5hLr3teCu06S8KTfHxSwtGkz0nAS%2BYS8v35JrIJ%2FxYDe3%2BtshuX2%2B2EZg3w%3D%3D" +
                "&callTp=L" +
                "&pageNo=$num2" +
                "&numOfRows=100&lifeArray=$lifeArray"
        val dbFactoty: DocumentBuilderFactory = DocumentBuilderFactory.newInstance();
        val dBuilder: DocumentBuilder = dbFactoty.newDocumentBuilder();
        val doc: Document = dBuilder.parse(urlstr)
        list.add(doc)
        if (doc.getElementsByTagName("servList").length == 100) {
            num2++
            categoryURL(num2, list)
        }
        return list
    }

    private fun getCharTrgterArray(
            num: Int,
            list: MutableList<Document>,
            charTrgterArray: String,
    )
            : MutableList<Document> {
        var num2 = num
        val urlstr = "http://www.bokjiro.go.kr/openapi/rest/gvmtWelSvc" +
                "?crtiKey=" +
                "keTuCooJ8R9Ao5LERVj48XiH87g5hLr3teCu06S8KTfHxSwtGkz0nAS%2BYS8v35JrIJ%2FxYDe3%2BtshuX2%2B2EZg3w%3D%3D" +
                "&callTp=L" +
                "&pageNo=$num2" +
                "&numOfRows=100&charTrgterArray=$charTrgterArray"
        val dbFactoty: DocumentBuilderFactory = DocumentBuilderFactory.newInstance();
        val dBuilder: DocumentBuilder = dbFactoty.newDocumentBuilder();
        val doc: Document = dBuilder.parse(urlstr)
        list.add(doc)
        if (doc.getElementsByTagName("servList").length == 100) {
            num2++
            categoryURL(num2, list)
        }
        return list
    }

    private fun getObstAbtArray(
            num: Int,
            list: MutableList<Document>,
            obstAbtArray: String,
    )
            : MutableList<Document> {
        var num2 = num
        val urlstr = "http://www.bokjiro.go.kr/openapi/rest/gvmtWelSvc" +
                "?crtiKey=" +
                "keTuCooJ8R9Ao5LERVj48XiH87g5hLr3teCu06S8KTfHxSwtGkz0nAS%2BYS8v35JrIJ%2FxYDe3%2BtshuX2%2B2EZg3w%3D%3D" +
                "&callTp=L" +
                "&pageNo=$num2" +
                "&numOfRows=100&obstAbtArray=$obstAbtArray"
        val dbFactoty: DocumentBuilderFactory = DocumentBuilderFactory.newInstance();
        val dBuilder: DocumentBuilder = dbFactoty.newDocumentBuilder();
        val doc: Document = dBuilder.parse(urlstr)
        list.add(doc)
        if (doc.getElementsByTagName("servList").length == 100) {
            num2++
            categoryURL(num2, list)
        }
        return list
    }

    private fun getTrgterIndvdlArray(
            num: Int,
            list: MutableList<Document>,
            trgterIndvdlArray: String,
    )
            : MutableList<Document> {
        var num2 = num
        val urlstr = "http://www.bokjiro.go.kr/openapi/rest/gvmtWelSvc" +
                "?crtiKey=" +
                "keTuCooJ8R9Ao5LERVj48XiH87g5hLr3teCu06S8KTfHxSwtGkz0nAS%2BYS8v35JrIJ%2FxYDe3%2BtshuX2%2B2EZg3w%3D%3D" +
                "&callTp=L" +
                "&pageNo=$num2" +
                "&numOfRows=100&trgterIndvdlArray=$trgterIndvdlArray"
        val dbFactoty: DocumentBuilderFactory = DocumentBuilderFactory.newInstance();
        val dBuilder: DocumentBuilder = dbFactoty.newDocumentBuilder();
        val doc: Document = dBuilder.parse(urlstr)
        list.add(doc)
        if (doc.getElementsByTagName("servList").length == 100) {
            num2++
            categoryURL(num2, list)
        }
        return list
    }

    private fun getDesireArray(
            num: Int,
            list: MutableList<Document>,
            desireArray: String,
    )
            : MutableList<Document> {
        var num2 = num
        val urlstr = "http://www.bokjiro.go.kr/openapi/rest/gvmtWelSvc" +
                "?crtiKey=" +
                "keTuCooJ8R9Ao5LERVj48XiH87g5hLr3teCu06S8KTfHxSwtGkz0nAS%2BYS8v35JrIJ%2FxYDe3%2BtshuX2%2B2EZg3w%3D%3D" +
                "&callTp=L" +
                "&pageNo=$num2" +
                "&numOfRows=100&desireArray=$desireArray"
        val dbFactoty: DocumentBuilderFactory = DocumentBuilderFactory.newInstance();
        val dBuilder: DocumentBuilder = dbFactoty.newDocumentBuilder();
        val doc: Document = dBuilder.parse(urlstr)
        list.add(doc)
        if (doc.getElementsByTagName("servList").length == 100) {
            num2++
            categoryURL(num2, list)
        }
        return list
    }
}