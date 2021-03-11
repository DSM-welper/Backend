package welper.welper.repository

import org.springframework.data.jpa.repository.JpaRepository
import welper.welper.domain.Post
import welper.welper.domain.User

interface PostRepository:JpaRepository<Post,Int>{
    fun findByIdAndUser(id: Int, user: User):Post?
}