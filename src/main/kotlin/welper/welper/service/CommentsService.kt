package welper.welper.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import welper.welper.controller.response.CommentResponse
import welper.welper.domain.Comments
import welper.welper.domain.Post
import welper.welper.domain.User
import welper.welper.exception.CommentsNotFoundException
import welper.welper.exception.PostNotFoundException
import welper.welper.exception.UserNotFoundException
import welper.welper.repository.CommentsRepository
import welper.welper.repository.PostRepository
import welper.welper.repository.UserRepository
import java.time.LocalDateTime
import java.time.ZoneId

@Service
class CommentsService(
        val jwtService: JwtService,
        val userRepository: UserRepository,
        val commentRepository: CommentsRepository,
        val postRepository: PostRepository,
) {
    fun commentListRead(postId: Int, pageable: Pageable): CommentResponse {
        val page: Page<Comments> = commentRepository.findAllByPostId(pageable = pageable, postId = postId)
        val list: MutableList<CommentResponse.Comment> = mutableListOf()
        page.forEach {
            list.add(CommentResponse.Comment(
                    sequence = it.sequence,
                    comment = it.comments,
                    depts = it.depts,
                    writer = it.user.name,
                    parents = it.parents,
                    id = it.id,
                    createdAt = it.createdAt
            ))
        }
        return CommentResponse(
                list = list,
                totalOfElements = page.totalElements,
                totalOfPage = page.totalPages,
        )
    }

    fun commentsParents(postId: Int, commentsId: Int, content: String, token: String) {
        val email: String = jwtService.getEmail(token)
        val user: User = userRepository.findByEmail(email) ?: throw UserNotFoundException(email)
        val post: Post = postRepository.findPostById(postId) ?: throw PostNotFoundException(email, postId)
        val comments: Comments = (commentRepository.findCommentsById(commentsId)
                ?: throw CommentsNotFoundException(commentsId))
        val allComments: List<Comments> = commentRepository.findAllByPostId(postId)
        val i = 1
        val i2 = countParents(postId, comments, i)
        println("ansser$i2")

        allComments.forEach {
            if (it.sequence >= comments.sequence + i2)
                it.updateSequence(it.sequence + 1)
            commentRepository.save(it)
        }

        commentRepository.save(
                Comments(
                        parents = commentsId,
                        depts = comments.depts + 1,
                        sequence = comments.sequence + i2,
                        comments = content,
                        postId = post.id,
                        user = user,
                        createdAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"))
                )
        )
    }

    fun commentsWrite(token: String, postId: Int, content: String) {
        val email: String = jwtService.getEmail(token)
        val user: User = userRepository.findByEmail(email) ?: throw UserNotFoundException(email)
        val post: Post = postRepository.findPostById(postId) ?: throw PostNotFoundException(email, postId)
        val comments: List<Comments?> = commentRepository.findAll()

        commentRepository.save(
                Comments(
                        parents = 0,
                        depts = 0,
                        sequence = comments.size + 1,
                        comments = content,
                        postId = post.id,
                        user = user,
                        createdAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"))
                )
        )
    }

    fun commentsDelete(token: String, postId: Int, commentsId: Int) {
        examineInformation(token, postId, commentsId)
        deleteCommentsChild(postId, commentsId)
        deleteComments(postId, commentsId)
    }

    private fun deleteCommentsChild(postId: Int, commentsId: Int) {
        val commentsChild: List<Comments> = commentRepository.findAllByPostIdAndParents(postId, commentsId)
        commentsChild.forEach {
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

    private fun deleteComments(postId: Int, commentsId: Int) {
        val comments: Comments = (commentRepository.findCommentsById(commentsId)
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

    private fun examineInformation(token: String, postId: Int, commentsId: Int) {
        val email: String = jwtService.getEmail(token)
        val user = userRepository.findByEmail(email) ?: throw UserNotFoundException(email)
        postRepository.findPostById(postId) ?: throw PostNotFoundException(email, postId)
        commentRepository.findByIdAndUser(commentsId, user) ?: throw CommentsNotFoundException(commentsId)
    }

    private fun countParents(postId: Int, comments: Comments, i: Int): Int {
        var i2 = i
        val commentsChild: List<Comments> = commentRepository.findAllByPostIdAndParents(postId, comments.id)
        if (commentsChild.isNotEmpty()) {
            repeat(commentsChild.size) {
                i2++
            }
            commentsChild.forEach {
                countParents(postId, it, i2)
            }
        }
        return i2
    }
}
