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

    fun postList(token: String, pageable: Pageable): PostListResponse {
        val page: Page<Post> = postRepository.findAll(pageable)
        val list: MutableList<PostListResponse.PostList> = mutableListOf()
        page.forEach {
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
                post = list,
                totalOfElements = page.totalElements,
                totalOfPage = page.totalPages,
        )
    }

    fun postCategoryRead(token: String, category: String, pageable: Pageable): PostListResponse {
        val list: MutableList<PostListResponse.PostList> = mutableListOf()
        val page: Page<Post> = postRepository.findAllByCategory(category, pageable)

        page.forEach {
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
                post = list,
                totalOfElements = page.totalElements,
                totalOfPage = page.totalPages,
        )
    }

    fun postMineRead(token: String, pageable: Pageable): PostListResponse {
        val email: String = jwtService.getEmail(token)
        val user: User = userRepository.findByEmail(email) ?: throw UserNotFoundException(email)
        val list: MutableList<PostListResponse.PostList> = mutableListOf()
        val page: Page<Post> = postRepository.findAllByUser(user, pageable)

        page.forEach {
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
                post = list,
                totalOfElements = page.totalElements,
                totalOfPage = page.totalPages,
        )
    }

    fun searchPost(token: String, content: String, pageable: Pageable): PostListResponse {
        val page: Page<Post> = postRepository.findPostBySearch(content, pageable)
        val list: MutableList<PostListResponse.PostList> = mutableListOf()
        page.forEach {
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
                post = list,
                totalOfElements = page.totalElements,
                totalOfPage = page.totalPages,
        )
    }


    fun test(numOfPage: Int): PostListResponse {
        val postList = postRepository.findAll()
        val list: MutableList<PostListResponse.PostList> = mutableListOf()
        postList.forEach {
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
        val pageList = getPageOfList(numOfPage, list)

        return PostListResponse(
                post = pageList,
                totalOfPage = (postList.size - 1) / 5 + 1,
                totalOfElements = postList.size.toLong()
        )
    }

    private fun getPageOfList(numOfPage: Int, postList: MutableList<PostListResponse.PostList>):
            MutableList<PostListResponse.PostList> {
        val numOfPostList: Int = (numOfPage - 1) * 5;
        val lastPostList: MutableList<PostListResponse.PostList> = mutableListOf();
        if (postList.size < numOfPostList + 1)
            throw NonNumOfPageOutOfBoundsException()
        val num = postList.size - numOfPostList

        if (num >= 5)
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
