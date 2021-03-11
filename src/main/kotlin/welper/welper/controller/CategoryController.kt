package welper.welper.controller


import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import welper.welper.controller.response.CategoryPostResponse
import welper.welper.service.CategoryService
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory

@RestController
@RequestMapping("/category")
class CategoryController(
        val categoryService: CategoryService,
) {
    @GetMapping("/{type}")
    fun cateGory(@PathVariable type:String): CategoryPostResponse {
        return categoryService.getCategory(type)
    }
    @GetMapping("/id")
    fun detailCategory(@PathVariable id:String){
//        categoryService.detailCategory()
    }
}