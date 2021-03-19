//package welper.welper.controller
//
//import com.fasterxml.jackson.databind.ObjectMapper
//import org.junit.jupiter.api.Test
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.test.context.TestConstructor
//import org.springframework.test.web.servlet.MockMvc
//import org.springframework.transaction.annotation.Transactional
//import welper.welper.controller.response.CategoryListPostResponse
//
//@Suppress("DEPRECATION")//사용되지 않는 경고 비활성화
//@SpringBootTest
//@AutoConfigureMockMvc
//@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
//@Transactional
//internal class CategoryControllerTest(
//        private val mock: MockMvc,
//        private val objectMapper: ObjectMapper,
//) {
//    @Test
//    fun `카테고리 리스트 ok`(){
//        val response = objectMapper.writeValueAsString(
//                CategoryListPostResponse
//        )
//    }
//
//}