package welper.welper.repository

import org.springframework.data.jpa.repository.JpaRepository
import welper.welper.domain.Post

interface PostRepository:JpaRepository<Post,String>{
}