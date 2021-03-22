package welper.welper.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import welper.welper.controller.response.CategoryListPostResponse
import welper.welper.controller.response.PostListResponse
import welper.welper.controller.response.PostResponse
import welper.welper.domain.Comments
import welper.welper.domain.Post
import welper.welper.domain.User
import welper.welper.exception.PostListNotFoundException
import welper.welper.exception.PostNotFoundException
import welper.welper.exception.UserNotFoundException
import welper.welper.repository.CommentsRepository
import welper.welper.repository.PostRepository
import welper.welper.repository.UserRepository
import java.time.LocalDateTime

@Service
class PostService(
        val jwtService: JwtService,
        val userRepository: UserRepository,
        val postRepository: PostRepository,
        val commentsRepository: CommentsRepository,
) {
    fun postCreate(token: String, title: String, content: String, category: String, createdAt: LocalDateTime) {
        val email: String = jwtService.getEmail(token)
        val user: User = userRepository.findByIdOrNull(email) ?: throw UserNotFoundException(email)
        postRepository.save(
                Post(
                        title = title,
                        content = content,
                        category = category,
                        createdAt = createdAt,
                        user = user,
                )
        )
    }

    fun postDelete(token: String, id: Int) {
        val email: String = jwtService.getEmail(token)
        val user: User = userRepository.findByIdOrNull(email) ?: throw UserNotFoundException(email)
        val post: Post = postRepository.findByIdAndUser(id, user) ?: throw PostNotFoundException(email, id)
        
        commentsRepository.deleteAllByPostId(post.id)
        postRepository.delete(post)
    }

    fun postDetailRead(token: String, id: Int): PostResponse {
        val email: String = jwtService.getEmail(token)
        val user: User = userRepository.findByIdOrNull(email) ?: throw UserNotFoundException(email)
        val post: Post = postRepository.findByIdOrNull(id) ?: throw PostNotFoundException(email, id)
        val comments: List<Comments?> = commentsRepository.findAllByPostId(id)

        val list: MutableList<PostResponse.CommentsResponse> = mutableListOf()
        comments.map {
            if (it != null)
                list.add(PostResponse.CommentsResponse(
                        id = it.id,
                        parents = it.parents,
                        depts = it.depts,
                        comments = it.comments,
                        postId = it.postId,
                        commentWriter = it.user.name,
                ))
        }

        return PostResponse(
                title = post.title,
                content = post.content,
                createdAt = post.createdAt,
                category = post.category,
                writer = user.name,
                comment = list,
        )
    }

    fun postList(token: String): PostListResponse {
        val post: List<Post?> = postRepository.findAll()
        val list: MutableList<PostListResponse.PostList> = mutableListOf()
        post.forEach {
            if (it != null) {
                list.add(
                        PostListResponse.PostList(
                                title = it.title,
                                id = it.id,
                                writer = it.user.name,
                                creatAt = it.createdAt,
                                category = it.category
                        )
                )
            }
        }
        return PostListResponse(
                post = list
        )
    }

    fun postCategoryRead(token: String, category: String): PostListResponse {
        val list: MutableList<PostListResponse.PostList> = mutableListOf()
        val post: List<Post?> = postRepository.findAllByCategory(category)

        post.forEach {
            if (it != null) {
                list.add(
                        PostListResponse.PostList(
                                title = it.title,
                                id = it.id,
                                writer = it.user.name,
                                creatAt = it.createdAt,
                                category = it.category
                        )
                )
            }
        }
        return PostListResponse(
                post = list
        )
    }

    fun postMineRead(token: String): PostListResponse {
        val email: String = jwtService.getEmail(token)
        val user: User = userRepository.findByIdOrNull(email) ?: throw UserNotFoundException(email)
        val list: MutableList<PostListResponse.PostList> = mutableListOf()
        val post: List<Post?> = postRepository.findAllByUser(user)

        post.forEach {
            if (it != null) {
                list.add(
                        PostListResponse.PostList(
                                title = it.title,
                                id = it.id,
                                writer = it.user.name,
                                creatAt = it.createdAt,
                                category = it.category
                        )
                )
            }
        }
        return PostListResponse(
                post = list
        )
    }

    fun searchPost(token: String, content: String): PostListResponse {

        val email: String = jwtService.getEmail(token)
        val user: User = userRepository.findByIdOrNull(email) ?: throw UserNotFoundException(email)

        val post: List<Post?> = postRepository.findAll()
        val list: MutableList<PostListResponse.PostList> = mutableListOf()
        post.forEach {
            if (it != null) {
                list.add(
                        PostListResponse.PostList(
                                title = it.title,
                                id = it.id,
                                writer = it.user.name,
                                creatAt = it.createdAt,
                                category = it.category
                        )
                )
            }
        }
        list.filter { it.title.contains(content) }
        return PostListResponse(
                post = list
        )
    }
}
