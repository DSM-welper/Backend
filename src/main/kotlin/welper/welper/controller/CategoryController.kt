//package welper.welper.controller
//
//import org.springframework.web.bind.annotation.GetMapping
//import org.springframework.web.bind.annotation.RequestMapping
//import org.springframework.web.bind.annotation.RestController
//import java.io.BufferedReader
//import java.io.InputStream
//import java.io.InputStreamReader
//import java.net.HttpURLConnection
//import java.net.URL
//
//@RestController
//@RequestMapping("/category")
//class CategoryController() {
//    @GetMapping
//    fun cateGory(){
//
//    }
//    @GetMapping("/all")
//    fun allCategory(){
//        val key :String = "A"
//        val result:StringBuffer
//        try{
//            val urlstr:String = "http://www.bokjiro.go.kr/openapi/rest/gvmtWelSvc?" +
//                    "crtikey$key" +
//                    "&callTp=$number" +
//                    "&pageNum=$num" +
//                    "&numOfRows=$num2" +
//                    "&lifeArray=$num3"
//            val url:URL = URL(urlstr)
//            val urlconnection:HttpURLConnection = url.openConnection() as HttpURLConnection
//            urlconnection.setRequestProperty("GET")
//
//            val br: BufferedReader =  BufferedReader(InputStreamReader(urlconnection.getInputStream(),"UTF-8"))
//
//            val returnLine : String
//
//        } catch (e:Exception){}
//    }
//
//
//}