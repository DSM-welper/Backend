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
        val comments: Comments = (commentRepository.findByIdOrNull(commentsId)
                ?: throw UserNotFoundException(commentsId.toString()))
        val commentsChild: List<Comments> = commentRepository.findAllByPostIdAndParents(postId, commentsId)
        val allComments: List<Comments> = commentRepository.findAllByPostId(postId)
        var i3: Int = 1
        println(commentsChild.size)

            repeat(commentsChild.size) {
                i3++
            }
        println("i3: "+comments.sequence+i3 )
        allComments.forEach {
            println(it.sequence)
            if (it.sequence >= comments.sequence + i3)
                it.updateSequence(it.sequence + 1)
            commentRepository.save(it)
        }
        commentRepository.save(
                Comments(
                        parents = commentsId,
                        depts = 0,
                        sequence = comments.sequence + i3,
                        comments = content,
                        postId = post.id,
                )
        )
    }

    fun commentsWrite(token: String, postId: Int, content: String) {
        val email: String = jwtService.getEmail(token)
        val user: User = userRepository.findByIdOrNull(email) ?: throw UserNotFoundException(email)
        val post: Post = postRepository.findByIdOrNull(postId) ?: throw PostNotFoundException(email, postId)
        val comments: List<Comments?> = commentRepository.findAllByParentsAndDepts(0, 0)
        var num: Int = 1
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