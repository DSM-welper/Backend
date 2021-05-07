package welper.welper.repository

import org.springframework.data.jpa.repository.JpaRepository
import welper.welper.domain.DeleteEmail

interface DeleteEmailRepository:JpaRepository<DeleteEmail,String> {
}