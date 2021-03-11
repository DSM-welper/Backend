//package welper.welper.service
//
//import org.w3c.dom.Element
//import org.w3c.dom.Node
//import org.w3c.dom.NodeList
//
//class GetAPIDataService {
//    private fun getTagValue(tag: String, eElement: Element): String? {
//        val nlList: NodeList = eElement.getElementsByTagName(tag).item(0).getChildNodes()
//        val nValue: Node = nlList.item(0) as Node ?: return null
//        return nValue.getNodeValue()
//    }
//}