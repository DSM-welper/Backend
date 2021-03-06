package welper.welper.repository

import org.springframework.data.jpa.repository.JpaRepository
import welper.welper.domain.User

interface UserRepository : JpaRepository<User, String> {
    fun findByEmail(email: String): User?
    fun existsByEmail(email: String): Boolean

}