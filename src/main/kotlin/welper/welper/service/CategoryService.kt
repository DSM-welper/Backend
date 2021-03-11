package welper.welper.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import welper.welper.controller.response.CategoryPostResponse
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory

@Service
class CategoryService(
        @Value("\${API_KEY}")
        private val key: String,
){

    fun getCategory(type:String):CategoryPostResponse{
        val urlstr = "http://www.bokjiro.go.kr/openapi/rest/gvmtWelSvc" +
                "?crtiKey=$key" +
                "&callTp=L" +
                "&pageNo=1" +
                "&numOfRows=100"

        val dbFactoty: DocumentBuilderFactory = DocumentBuilderFactory.newInstance();
        val dBuilder: DocumentBuilder = dbFactoty.newDocumentBuilder();
        val doc: Document = dBuilder.parse(urlstr)

        return CategoryPostResponse(
                wantedList = getWantedList(doc),
                servList = createServList(doc)
        )
    }

    private fun createServList(doc:Document): List<CategoryPostResponse.ServList> {

        val nList: NodeList = doc.getElementsByTagName("servList");

        val list: ArrayList<CategoryPostResponse.ServList> = ArrayList()
        for (i in 1 until nList.length) {
            val nNode: Node = nList.item(i)
            val eElement = nNode as Element
            val a = CategoryPostResponse.ServList(
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

    private fun getWantedList(doc:Document):String {
        return doc.getDocumentElement().getNodeName()
    }

    private fun getTagValue(tag: String, eElement: Element): String? {
        val nlList: NodeList = eElement.getElementsByTagName(tag).item(0).getChildNodes()
        val nValue: Node = nlList.item(0) as Node ?: return null
        return nValue.getNodeValue()
    }
}