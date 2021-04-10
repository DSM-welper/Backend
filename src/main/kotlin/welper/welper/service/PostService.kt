package welper.welper.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import welper.welper.controller.request.CommentsRequest
import welper.welper.controller.response.CategoryListPostResponse
import welper.welper.controller.response.CommentResponse
import welper.welper.controller.response.PostListResponse
import welper.welper.controller.response.PostResponse
import welper.welper.domain.Comments
import welper.welper.domain.Post
import welper.welper.domain.User
import welper.welper.exception.NonNumOfPageOutOfBoundsException
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
        val user: User = userRepository.findByEmail(email) ?: throw UserNotFoundException(email)
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
        val user: User = userRepository.findByEmail(email) ?: throw UserNotFoundException(email)
        val post: Post = postRepository.findByIdAndUser(id, user) ?: throw PostNotFoundException(email, id)

        commentsRepository.deleteAllByPostId(post.id)
        postRepository.delete(post)
    }

    fun postDetailRead(token: String, id: Int): PostResponse {
        val email: String = jwtService.getEmail(token)
        val user: User = userRepository.findByEmail(email) ?: throw UserNotFoundException(email)
        val post: Post = postRepository.findPostById(id) ?: throw PostNotFoundException(email, id)
        val list: MutableList<PostResponse.CommentsResponse> = mutableListOf()

        return PostResponse(
                title = post.title,
                content = post.content,
                createdAt = post.createdAt,
                category = post.category,
                writer = user.name,
                id = post.id,
        )
    }

    fun postList(token: String, numOfPage: Int): PostListResponse {
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
        val lastPostList = getPageOfList(numOfPage, list)
        return PostListResponse(
                post = lastPostList,
                totalPage = list.size/5
        )
    }

    fun postCategoryRead(token: String, categoryId: String,numOfPage: Int): PostListResponse {
        val list: MutableList<PostListResponse.PostList> = mutableListOf()
        val post: List<Post?> = postRepository.findAllByCategory(categoryId)

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

        val lastPostList = getPageOfList(numOfPage, list)
        return PostListResponse(
                post = lastPostList,
                totalPage = list.size/5

        )
    }

    fun postMineRead(token: String, numOfPage: Int): PostListResponse {
        val email: String = jwtService.getEmail(token)
        val user: User = userRepository.findByEmail(email) ?: throw UserNotFoundException(email)
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
        val lastPostList = getPageOfList(numOfPage, list)

        return PostListResponse(
                post = lastPostList,
                totalPage = list.size/5
        )
    }

    fun searchPost(token: String, content: String, numOfPage: Int): PostListResponse {

        val email: String = jwtService.getEmail(token)
        userRepository.findByEmail(email) ?: throw UserNotFoundException(email)

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
                post = list,
                totalPage = list.size/5
        )
    }

    private fun getPageOfList(numOfPage: Int, postList: MutableList<PostListResponse.PostList>):
            MutableList<PostListResponse.PostList> {
        val numOfPostList: Int = numOfPage * 5;
        val lastPostList: MutableList<PostListResponse.PostList> = mutableListOf();
        if (postList.size < numOfPostList)
            throw NonNumOfPageOutOfBoundsException()
        val num = postList.size - numOfPostList - 1
        if (num > 5)
            for (i in numOfPostList until (numOfPostList + 5)) {
                lastPostList.add(postList[i])
            }
        else
            for (i in numOfPostList until (numOfPostList + num)) {
                lastPostList.add(postList[i])
            }

        return lastPostList
    }
}

