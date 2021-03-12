package welper.welper.repository

import org.springframework.data.jpa.repository.JpaRepository
import welper.welper.domain.Comments

interface CommentsRepository:JpaRepository<Comments,Int> {

}