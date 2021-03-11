package welper.welper.controller


import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.w3c.dom.Document
import org.w3c.dom.NodeList
import java.net.URL
import javax.xml.parsers.*

@RestController
@RequestMapping("/category")
class CategoryController(
) {
    @GetMapping
    fun cateGory() {
        val urlstr = "http://www.bokjiro.go.kr/openapi/rest/gvmtWelSvc?crtiKey=keTuCooJ8R9Ao5LERVj48XiH87g5hLr3teCu06S8KTfHxSwtGkz0nAS%2BYS8v35JrIJ%2FxYDe3%2BtshuX2%2B2EZg3w%3D%3D&callTp=L&pageNo=10&numOfRows=10"
        val url = URL(urlstr)
        val dbFactoty: DocumentBuilderFactory = DocumentBuilderFactory.newInstance();
        val dBuilder: DocumentBuilder = dbFactoty.newDocumentBuilder();
        val doc: Document = dBuilder.parse(urlstr)

        doc.getDocumentElement().normalize();
       println("Root element: " + doc.getDocumentElement().getNodeName());
        val nList: NodeList = doc.getElementsByTagName("servList");
        println("파싱할 리스트 수 : "+ nList.getLength());  // 파싱할 리스트 수 :  5
    }
}