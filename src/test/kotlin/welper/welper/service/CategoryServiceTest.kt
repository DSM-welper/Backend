//package welper.welper.service
//
//import com.nhaarman.mockitokotlin2.any
//import com.nhaarman.mockitokotlin2.doReturn
//import com.nhaarman.mockitokotlin2.eq
//import com.nhaarman.mockitokotlin2.mock
//import org.assertj.core.api.Assertions
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.assertThrows
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.beans.factory.annotation.Value
//import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer
//import org.springframework.test.context.ContextConfiguration
//import org.springframework.test.context.TestPropertySource
//import welper.welper.domain.OpenApICategory
//import welper.welper.domain.OpenApiPost
//import welper.welper.domain.User
//import welper.welper.domain.attribute.Category
//import welper.welper.domain.attribute.Gender
//import welper.welper.domain.attribute.Marry
//import welper.welper.exception.NonExistCategoryDetailException
//@TestPropertySource
//internal class CategoryServiceTest(
//) {
//
//
//    @Test
//    fun `유저 카테고리 가져오기 ok`() {
//        val category = categoryService.userCategory(
//                token = "this-is-test-token",
//        )
//        Assertions.assertThat(category.genderList.list).map<String> { it.servId }.containsAll(listOf("WII00000001"))
//
//    }
//
//    @Test
//    fun `유저 카테고리 가져오기 토큰 오류`() {
//        assertThrows<NullPointerException> {
//            categoryService.userCategory(
//                    token = "this-is-test-token2",
//            )
//        }
//    }
//
//    @Test
//    fun `랜덤 카테고리 가져오기 ok`() {
//        val category = categoryService.randomCategory()
//
//        Assertions.assertThat(category.genderList.list).map<String> { it.servId }.containsAll(listOf("WII00000001"))
//    }
//
//    @Test
//    fun `전체 카테고리 가져오기 ok`() {
//        val category = categoryService.getAllCategory()
//        Assertions.assertThat(category.servList).map<String> { it.servId }.containsAll(listOf("WII00000001"))
//    }
//
//    @Test
//    fun `조건으로로 카테고리 가오기 ok`() {
//        val category = categoryService.getCategory(
//                categoryNameList = listOf(Category.WOMEN)
//        )
//        Assertions.assertThat(category.servList).map<String> { it.servId }.containsAll(listOf("WII00000001"))
//    }
//
//    @Test
//    fun `전체 검색해서 가져오기 ok`() {
//        val category = categoryService.categorySearch(
//                content = "나야"
//        )
//        Assertions.assertThat(category.servList).map<String> { it.servId }.containsAll(listOf("WII00000001"))
//    }
//
//    @Test
//    fun `카테고리 자세히보기 ok`() {
//        val category = categoryService.detailCategory(
//                id = "WII00000001"
//        )
//        Assertions.assertThat(category.servId).isEqualTo("WII00000001")
//
//    }
//
//    @Test
//    fun `카테고리 없는 id로 자세히보기 ok`() {
//        assertThrows<NonExistCategoryDetailException> {
//            categoryService.detailCategory(
//                    id = "WII00000001w222222"
//            )
//        }
//    }
//
//    private val user = User(
//            email =
//            "test@email.com",
//            password = "e9e633097ab9ceb3e48ec3f70ee2beba41d05d5420efee5da85f97d97005727587fda33ef4ff2322088f4c79e8133cc9cd9f3512f4d3a303cbdb5bc585415a00",
//            age = 13,
//            name = "test",
//            marry = Marry.DO,
//            disorder = false,
//            gender = Gender.WOMEN,
//    )
//
//    private val open_api_post = OpenApiPost(
//            servId = "WII00000001",
//            servNm = "3995",
//            inqNum = "국가보훈처",
//            jurMnofNm = "선양정책과",
//            jurOrgNm = "나야",
//            servDgst = "나야",
//            servDtlLink = "나야",
//            svcfrstRegTs = "2020-07-22"
//    )
//
//    private val open_api_category = OpenApICategory(
//            id = 1,
//            categoryName = "여성",
//            openApiPost = open_api_post,
//    )
//
//    private val categoryService = CategoryService(
//            openApiCategoryRepository = mock {
//                on { findAllByCategoryName(Category.WOMEN.value) } doReturn mutableSetOf(open_api_category)
//                on { findSeveralByCategory(listOf(Category.WOMEN.value)) } doReturn mutableListOf(open_api_category)
//            },
//            openApiPostRepository = mock {
//                on { findAll() } doReturn listOf(open_api_post)
//            },
//            jwtService = mock {
//                on { createToken(eq("test@email.com"), any()) } doReturn "this-is-test-token"
//                on { getEmail(eq("this-is-test-token")) } doReturn "test@email.com"
//                on { isValid(eq("this-is-test-token")) } doReturn true
//            },
//            userRepository = mock {
//                on { findByEmail("test@email.com") } doReturn user
//            },
//            key = key,
//    )
//}