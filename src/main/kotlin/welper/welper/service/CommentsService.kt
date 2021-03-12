package welper.welper.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import welper.welper.domain.User
import welper.welper.exception.UserNotFoundException
import welper.welper.repository.CommentsRepository
import welper.welper.repository.UserRepository

@Service
class CommentsService (
        val jwtService: JwtService,
        val userRepository: UserRepository,
        val commentRepository:CommentsRepository,
        ){
    fun commentWrite(postId:Int, commentsId:Int, content:String,token:String){
        val email:String =jwtService.getEmail(token)
        val user: User = userRepository.findByIdOrNull(email)?: throw UserNotFoundException(email)
        
    }
}