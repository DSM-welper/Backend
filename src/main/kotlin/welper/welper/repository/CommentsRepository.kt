package welper.welper.repository

import org.springframework.data.jpa.repository.JpaRepository
import welper.welper.domain.Comments

interface CommentsRepository : JpaRepository<Comments, Int> {
    fun findAllByParentsAndDepts(parents: Int, depts: Int): List<Comments?>
    fun findAllByPostId(postId: Int):List<Comments>
    fun findAllByPostIdAndParents(postId: Int, parents: Int):List<Comments>

}