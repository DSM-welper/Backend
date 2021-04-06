package welper.welper.repository

import org.springframework.data.jpa.repository.JpaRepository
import welper.welper.domain.Post
import welper.welper.domain.User
import java.util.*

interface PostRepository:JpaRepository<Post,Int>{
    fun findByIdAndUser(id: Int, user: User):Post?
    fun findAllByCategory(category: String):List<Post?>
    fun findAllByUser(user: User):List<Post?>
    fun findPostById(id: Int): Post?
}