package welper.welper.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import welper.welper.domain.Post
import welper.welper.domain.User
import java.util.*

interface PostRepository:JpaRepository<Post,Int>{
    fun findByIdAndUser(id: Int, user: User):Post?
    fun findAllByCategory(category: String,pageable: Pageable):Page<Post>
    fun findAllByUser(user: User,pageable: Pageable):Page<Post>
    fun findPostById(id: Int): Post?
    @Query("Select c from Post c where c.title like %:content%" )
    fun findPostBySearch(content:String,pageable:Pageable):Page<Post>
}