package com.fhdufhdu.nocssboard.domain.board.service

import com.fhdufhdu.nocssboard.domain.board.service.dto.BoardServiceDto
import com.fhdufhdu.nocssboard.domain.board.service.dto.BoardServiceException
import com.fhdufhdu.nocssboard.domain.user.service.dto.UserServiceException
import com.fhdufhdu.nocssboard.entity.Comment
import com.fhdufhdu.nocssboard.entity.Post
import com.fhdufhdu.nocssboard.repository.comment.CommentRepository
import com.fhdufhdu.nocssboard.repository.post.PostRepository
import com.fhdufhdu.nocssboard.repository.post.dto.PostRepositoryDto
import com.fhdufhdu.nocssboard.repository.user.UserRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class BoardService(
        private val postRepository: PostRepository,
        private val commentRepository: CommentRepository,
        private val userRepository: UserRepository
) {
    fun findPosts(input: BoardServiceDto.FindPosts.Input): BoardServiceDto.FindPosts.Return {
        val dslDirection = input.sort.direction.queryDslOrder
        val dslOrderCriteria = PostRepositoryDto.FindAllBySearch.OrderCriteria.from(input.sort.criteria.value)

        // 페이지네이션 조건 객체
        val pageable = PageRequest.of(input.page.number, input.page.size)
        // 정렬 조건 객체
        val orderCondition = PostRepositoryDto.FindAllBySearch.OrderCondition(dslDirection, dslOrderCriteria)
        // 검색 조건 객체
        var searchCondition: PostRepositoryDto.FindAllBySearch.SearchCondition? = null
        if (input.search != null) {
            searchCondition = PostRepositoryDto.FindAllBySearch.SearchCondition(
                    input.search.searchQuery,
                    input.search.searchCriteria.value
            )
        }

        // 객체 조회
        val page = postRepository.findAllBySearch(searchCondition, orderCondition, pageable)

        val postDtoList = page.content.stream().map {
            BoardServiceDto.FindPosts.Return.Post(it.id, it.userId, it.title, it.content, it.createdAt, it.updatedAt)
        }.toList()

        return BoardServiceDto.FindPosts.Return(postDtoList, page.number, page.totalPages)
    }

    fun findOnePost(postId: Long): BoardServiceDto.FindOnePost.Return {
        val post = postRepository.findByIdAndStatus(postId, Post.Status.PUBLISHED)
                ?: throw BoardServiceException.NotFoundPost()
        return BoardServiceDto.FindOnePost.Return(post.id!!, post.user.id, post.title, post.content, post.createdAt, post.updatedAt)
    }

    fun addPost(title: String, content: String, writerId: String) {
        val writer = userRepository.findByIdOrNull(writerId)
                ?: throw UserServiceException.NotMatchSessionToUser()
        val newPost = Post(writer, title, content)
        postRepository.save(newPost)
    }

    fun deletePost(postId: Long, userId: String) {
        val post = postRepository.findByIdAndStatus(postId, Post.Status.PUBLISHED)
                ?: throw BoardServiceException.NotFoundPost()

        if (post.user.id != userId) throw BoardServiceException.NotWriterOfPost()

        post.changeStatus(Post.Status.DELETED)
        postRepository.save(post)
    }

    fun findComments(postId: Long, pageNumber: Int, pageSize: Int): BoardServiceDto.FindComments.Return {
        val existPost = postRepository.existsByIdAndStatus(postId, Post.Status.PUBLISHED)
        if (!existPost) throw BoardServiceException.NotFoundPost()

        val pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "createdAt"))
        val page = commentRepository.findAllByPostIdAndStatus(postId, Comment.Status.PUBLISHED, pageable)

        val commentDtoList = page.content.map {
            BoardServiceDto.FindComments.Return.Comment(it.id!!, it.user.id, it.content, it.createdAt, it.updatedAt)
        }

        return BoardServiceDto.FindComments.Return(commentDtoList, page.number, page.totalPages)
    }

    fun addComment(content: String, postId: Long, writerId: String) {
        val writer = userRepository.findByIdOrNull(writerId)
                ?: throw UserServiceException.NotMatchSessionToUser()
        val post = postRepository.findByIdAndStatus(postId, Post.Status.PUBLISHED)
                ?: throw BoardServiceException.NotFoundPost()


        val newComment = Comment(writer, post, content)
        commentRepository.save(newComment)
    }

    fun deleteComment(postId: Long, commentId: Long, userId: String) {
        val existPost = postRepository.existsByIdAndStatus(postId, Post.Status.PUBLISHED)
        if (!existPost) throw BoardServiceException.NotFoundComment()

        val comment = commentRepository.findByIdAndPostIdAndStatus(commentId, postId, Comment.Status.PUBLISHED)
                ?: throw BoardServiceException.NotFoundComment()

        if (comment.post.status == Post.Status.DELETED) throw BoardServiceException.NotFoundComment()
        if (comment.user.id != userId) throw BoardServiceException.NotWriterOfComment()

        comment.changeStatus(Comment.Status.DELETED)
        commentRepository.save(comment)
    }
}