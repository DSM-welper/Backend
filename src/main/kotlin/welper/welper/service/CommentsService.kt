package welper.welper.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import welper.welper.domain.Comments
import welper.welper.domain.Post
import welper.welper.domain.User
import welper.welper.exception.CommentsNotFoundException
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
                ?: throw CommentsNotFoundException(commentsId))
        val commentsChild: List<Comments> = commentRepository.findAllByPostIdAndParents(postId, commentsId)
        val allComments: List<Comments> = commentRepository.findAllByPostId(postId)
        var i3: Int = 1

        repeat(commentsChild.size) {
            i3++
        }
        println("i3: " + comments.sequence + i3)
        allComments.forEach {
            println(it.sequence)
            if (it.sequence >= comments.sequence + i3)
                it.updateSequence(it.sequence + 1)
            commentRepository.save(it)
        }
        commentRepository.save(
                Comments(
                        parents = commentsId,
                        depts = comments.depts + 1,
                        sequence = comments.sequence + i3,
                        comments = content,
                        post = post,
                        user = user,
                )
        )
    }

    fun commentsWrite(token: String, postId: Int, content: String) {
        val email: String = jwtService.getEmail(token)
        val user: User = userRepository.findByIdOrNull(email) ?: throw UserNotFoundException(email)
        val post: Post = postRepository.findByIdOrNull(postId) ?: throw PostNotFoundException(email, postId)
        val comments: List<Comments?> = commentRepository.findAll()


        commentRepository.save(
                Comments(
                        parents = 0,
                        depts = 0,
                        sequence = comments.size + 1,
                        comments = content,
                        post = post,
                        user = user,
                )
        )
    }

    fun commentsDelete(token: String, postId: Int, commentsId: Int) {
        examineInformation(token, postId)
        deleteComments(postId, commentsId)
        deleteCommentsChild(postId, commentsId)
    }

    fun deleteCommentsChild(postId: Int, commentsId: Int) {
        val commentsChild: List<Comments> = commentRepository.findAllByPostIdAndParents(postId, commentsId)
        commentsChild.forEach {
            deleteComments(postId, it.id)
            val allComments: List<Comments> = commentRepository.findAllByPostId(postId)
            allComments.forEach { it2 ->
                println("it: " + it.sequence + "it2: " + it2.sequence)
                if (it2.sequence > it.sequence) {
                    it2.updateSequence(it2.sequence - 1)
                    commentRepository.save(it2)
                }
            }
            commentRepository.deleteById(it.id)
        }
    }

    fun deleteComments(postId: Int, commentsId: Int) {
        val comments: Comments = (commentRepository.findByIdOrNull(commentsId)
                ?: throw CommentsNotFoundException(commentsId))
        val allComments: List<Comments> = commentRepository.findAllByPostId(postId)
        commentRepository.delete(comments)
        allComments.forEach {
            if (it.sequence > comments.sequence) {
                it.updateSequence(it.sequence - 1)
                commentRepository.save(it)
            }
        }
    }

    fun examineInformation(token: String, postId: Int) {
        val email: String = jwtService.getEmail(token)
        val user: User = userRepository.findByIdOrNull(email) ?: throw UserNotFoundException(email)
        val post: Post = postRepository.findByIdOrNull(postId) ?: throw PostNotFoundException(email, postId)
    }
}
