package welper.welper.repository

import org.springframework.data.jpa.repository.JpaRepository
import welper.welper.domain.Comments
import welper.welper.domain.User

interface CommentsRepository : JpaRepository<Comments, Int> {
    fun findAllByPostId(postId: Int):List<Comments>
    fun findAllByPostIdAndParents(postId: Int, parents: Int):List<Comments>
    fun findByIdAndUser(id: Int, user: User):Comments?
    fun deleteAllByPostId(postId: Int)
}