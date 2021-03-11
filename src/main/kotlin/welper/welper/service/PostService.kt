package welper.welper.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import welper.welper.controller.response.PostResponse
import welper.welper.domain.Post
import welper.welper.domain.User
import welper.welper.exception.UserNotFoundException
import welper.welper.repository.PostRepository
import welper.welper.repository.UserRepository
import java.time.LocalDateTime

@Service
class PostService(
        val jwtService: JwtService,
        val userRepository: UserRepository,
        val postRepository: PostRepository,
) {
    fun postCreate(token: String,title: String,content:String,category:String ,createdAt:LocalDateTime) {
        val email:String =jwtService.getEmail(token)
        val user: User = userRepository.findByIdOrNull(email)?: throw UserNotFoundException(email)
        postRepository.save(
                Post(
                        title= title,
                        content = content,
                        category = category,
                        createdAt = createdAt,
                        user = user,
                )
        )
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