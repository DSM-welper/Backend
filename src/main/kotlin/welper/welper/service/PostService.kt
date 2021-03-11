package welper.welper.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import welper.welper.domain.User
import welper.welper.exception.UserNotFoundException
import welper.welper.repository.UserRepository

@Service
class PostService(
        val jwtService: JwtService,
        val userRepository: UserRepository,
) {
    fun postCreate(token: String) {
        val email:String =jwtService.getEmail(token)
        val user: User = userRepository.findByIdOrNull(email)?: throw UserNotFoundException(email)

    }

    fun postDelete(token: String, id: Int) {
        val email:String =jwtService.getEmail(token)
        val user: User = userRepository.findByIdOrNull(email)?: throw UserNotFoundException(email)
    }

    fun postRead(token: String, id: Int) {
        val email:String =jwtService.getEmail(token)
        val user: User = userRepository.findByIdOrNull(email)?: throw UserNotFoundException(email)
    }

    fun postList(token: String) {
        val email:String =jwtService.getEmail(token)
        val user: User = userRepository.findByIdOrNull(email)?: throw UserNotFoundException(email)

    }
}