package welper.welper.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional
import welper.welper.domain.Comments
import welper.welper.domain.User

interface CommentsRepository : JpaRepository<Comments, Int> {
    fun findAllByPostId(postId: Int):List<Comments>
    fun findAllByPostIdAndParents(postId: Int, parents: Int):List<Comments>
    fun findByIdAndUser(id: Int, user: User):Comments?
    fun findCommentsById(id: Int):Comments?
    @Transactional
    fun deleteAllByPostId(postId: Int)
    fun findAllByPostId(postId: Int,pageable: Pageable): Page<Comments>
}