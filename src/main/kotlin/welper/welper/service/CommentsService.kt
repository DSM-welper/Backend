package welper.welper.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import welper.welper.domain.Comments
import welper.welper.domain.Post
import welper.welper.domain.User
import welper.welper.exception.PostNotFoundException
import welper.welper.exception.UserNotFoundException
import welper.welper.repository.CommentsRepository
import welper.welper.repository.PostRepository
import welper.welper.repository.UserRepository

@Service
class CommentsService(
        val jwtService: JwtService,
        val userRepository: UserRepository,
        val commentRepository: CommentsRepository,
        val postRepository: PostRepository,
) {
    fun commentsParents(postId: Int, commentsId: Int, content: String, token: String) {
        val email: String = jwtService.getEmail(token)
        val user: User = userRepository.findByIdOrNull(email) ?: throw UserNotFoundException(email)
        val post: Post = postRepository.findByIdOrNull(postId) ?: throw PostNotFoundException(email, postId)
        val comments: List<Comments?> = commentRepository.findAllByParentsAndDepts(0, 0)
        var num: Int = 0
        for (i in 1..comments.size)
            num++

        commentRepository.save(
                Comments(
                        parents = 0,
                        depts = 0,
                        sequence = num,
                        comments = content,
                        postId = post.id,
                )
        )
    }
}