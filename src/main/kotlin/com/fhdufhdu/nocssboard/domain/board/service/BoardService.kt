package com.fhdufhdu.nocssboard.domain.board.service

import com.fhdufhdu.nocssboard.domain.board.service.dto.command.PostSummariesCommand
import com.fhdufhdu.nocssboard.domain.board.service.dto.result.CommentDetails
import com.fhdufhdu.nocssboard.domain.board.service.dto.result.PostDetail
import com.fhdufhdu.nocssboard.domain.board.service.dto.result.PostSummaries
import com.fhdufhdu.nocssboard.domain.board.service.dto.result.SavedPost
import com.fhdufhdu.nocssboard.domain.user.service.UserServiceException
import com.fhdufhdu.nocssboard.entity.Comment
import com.fhdufhdu.nocssboard.entity.Post
import com.fhdufhdu.nocssboard.repository.comment.CommentRepository
import com.fhdufhdu.nocssboard.repository.post.PostRepository
import com.fhdufhdu.nocssboard.repository.post.dto.condition.PostSummariesCondition
import com.fhdufhdu.nocssboard.repository.user.UserRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class BoardService(
    private val postRepository: PostRepository,
    private val commentRepository: CommentRepository,
    private val userRepository: UserRepository
) {
    fun filterPostSummaries(input: PostSummariesCommand): PostSummaries {
        val orderCondition = PostSummariesCondition.Order(input.sort.direction.queryDslOrder, input.sort.criteria.value)
        val searchCondition = PostSummariesCondition.Search(input.search?.searchQuery, input.search?.searchCriteria?.value)

        // 페이지네이션 조건 객체
        val pageable = PageRequest.of(input.page.number, input.page.size)

        // 객체 조회
        val page = postRepository.findPostSummaries(PostSummariesCondition(searchCondition, orderCondition), pageable)

        val postDtoList = page.content.stream().map {
            PostSummaries.PostSummary(it.id, it.userId, it.title, it.content, it.createdAt, it.updatedAt)
        }.toList()

        return PostSummaries(postDtoList, page.number, page.totalPages)
    }

    fun fetchPostDetail(postId: Long): PostDetail {
        val post = postRepository.findByIdAndStatus(postId, Post.Status.PUBLISHED)
            ?: throw BoardServiceException.NotFoundPost()
        return PostDetail(
            post.id!!,
            post.user.id,
            post.title,
            post.content,
            post.createdAt,
            post.updatedAt
        )
    }

    fun addPost(title: String, content: String, writerId: String): SavedPost {
        val writer = userRepository.findByIdOrNull(writerId)
            ?: throw UserServiceException.NotMatchSessionToUser()
        val newPost = Post(writer, title, content)
        val savedPost = postRepository.save(newPost)
        return SavedPost(savedPost.id!!)
    }

    fun deletePost(postId: Long, userId: String) {
        val post = postRepository.findByIdAndStatus(postId, Post.Status.PUBLISHED)
            ?: throw BoardServiceException.NotFoundPost()

        if (post.user.id != userId) throw BoardServiceException.NotWriterOfPost()

        post.changeStatus(Post.Status.DELETED)
        postRepository.save(post)
    }

    fun fetchCommentDetails(postId: Long, pageNumber: Int, pageSize: Int): CommentDetails {
        val existPost = postRepository.existsByIdAndStatus(postId, Post.Status.PUBLISHED)
        if (!existPost) throw BoardServiceException.NotFoundPost()

        val pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "created_at"))
        val page = commentRepository.findAllByPostIdAndStatus(postId, Comment.Status.PUBLISHED, pageable)

        val commentDtoList = page.content.map {
            CommentDetails.CommentDetail(it.id!!, it.user.id, it.content, it.createdAt, it.updatedAt)
        }

        return CommentDetails(commentDtoList, page.number, page.totalPages)
    }

    fun addComment(content: String, postId: Long, writerId: String) {
        val writer = userRepository.findByIdOrNull(writerId)
            ?: throw UserServiceException.NotMatchSessionToUser()
        val post = postRepository.findByIdAndStatus(postId, Post.Status.PUBLISHED)
            ?: throw BoardServiceException.NotFoundPost()


        val newComment = Comment(writer, post, content)
        commentRepository.save(newComment)
    }

    fun removeComment(postId: Long, commentId: Long, userId: String) {
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