package welper.welper.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import welper.welper.domain.Post
import welper.welper.domain.User
import java.util.*

interface PostRepository:JpaRepository<Post,Int>{
    fun findByIdAndEmail(id: Int, email: String, ):Post?
    fun findAllByCategory(category: String,pageable: Pageable):Page<Post>
    fun findAllByEmail(email: String, pageable: Pageable):Page<Post>
    fun findPostById(id: Int): Post?
    @Query("Select c from Post c where c.title like %:content%" )
    fun findPostBySearch(content:String,pageable:Pageable):Page<Post>
}