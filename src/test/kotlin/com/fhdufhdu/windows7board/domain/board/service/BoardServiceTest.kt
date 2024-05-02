package com.fhdufhdu.windows7board.domain.board.service

import com.fhdufhdu.windows7board.Windows7BoardApplicationTests
import com.fhdufhdu.windows7board.common.dto.SortDirection
import com.fhdufhdu.windows7board.domain.board.service.dto.command.PostSummariesCommand
import com.fhdufhdu.windows7board.domain.user.service.UserServiceException
import com.fhdufhdu.windows7board.entity.Post
import com.fhdufhdu.windows7board.entity.User
import com.fhdufhdu.windows7board.repository.comment.CommentRepository
import com.fhdufhdu.windows7board.repository.post.PostRepository
import com.fhdufhdu.windows7board.repository.post.dto.condition.PostSummariesCondition
import com.fhdufhdu.windows7board.repository.post.dto.projection.PostSummariesProjection
import com.fhdufhdu.windows7board.repository.user.UserRepository
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import java.sql.Timestamp
import java.util.*

@ExtendWith(MockitoExtension::class)
class BoardServiceTest {
    private val postRepository = Mockito.mock(PostRepository::class.java)
    private val commentRepository = Mockito.mock(CommentRepository::class.java)
    private val userRepository = Mockito.mock(UserRepository::class.java)
    private val boardService = BoardService(postRepository, commentRepository, userRepository)

    private fun <T> any(type: Class<T>): T = Mockito.any<T>(type)
    @Test
    fun 게시글_목록_조회() {
        //given
        val input = PostSummariesCommand(
            PostSummariesCommand.Page(0, 10),
            PostSummariesCommand.Sort(PostSummariesCommand.SortCriteria.TITLE, SortDirection.DESC),
        )
        val content = "12345678901234567890123456789012345678901234567890"
        Mockito.`when`(
            postRepository.findPostSummaries(
                any(PostSummariesCondition::class.java),
                any(Pageable::class.java)
            )
        ).thenReturn(
            PageImpl(
                arrayOf(
                    PostSummariesProjection(
                        1,
                        "userId",
                        "title",
                        content.substring(0, 30),
                        Timestamp(System.currentTimeMillis()),
                        Timestamp(System.currentTimeMillis())
                    )
                ).toList(),
                PageRequest.of(input.page.number, input.page.size),
                1
            )
        )
        // when
        val result = boardService.filterPostSummaries(input)

        // then
        assert(result.number == 0)
        assert(result.totalPages == 1)
        assert(result.totalElements == 1L)
        assert(result.posts.size == 1)
        assert(result.posts.get(0).id == 1L)
    }

    @Test
    fun 게시글_상세_정보_조회() {
        // given
        val postId = 1.toLong()
        val userId = "fhdufhdu"
        Mockito.`when`(
            postRepository.findByIdAndStatus(postId, Post.Status.PUBLISHED)
        ).thenReturn(Post(User(userId, "1234"), "title", "content", postId))

        // when
        val result = boardService.fetchPostDetail(postId)

        // then
        assert(result.id == postId)
        assert(result.title == "title")
        assert(result.content == "content")
    }

    @Test
    fun 게시글_상세_정보_조회_실패() {
        // given
        val postId = 1L
        Mockito.`when`(
            postRepository.findByIdAndStatus(postId, Post.Status.PUBLISHED)
        ).thenReturn(null)

        // when/then
        assertThrows<BoardServiceException.NotFoundPost> {
            val result = boardService.fetchPostDetail(postId)
        }
    }

    @Test
    fun 게시글_추가() {
        // given
        val title = "title"
        val content = "content"
        val writerId = "id"
        val user = User(writerId, "password")
        val savedPost = Post(user, title, content, 1)

        Mockito.`when`(userRepository.findById(writerId)).thenReturn(Optional.of(user))
        Mockito.`when`(postRepository.save(any(Post::class.java))).thenReturn(savedPost)

        // when
        val result = boardService.addPost(title, content, writerId)

        // then
        assert(result.id == savedPost.id)
    }

    @Test
    fun 게시글_추가_실패_세션_정보가_올바르지_않음() {
        // given
        val title = "title"
        val content = "content"
        val writerId = "id"

        Mockito.`when`(userRepository.findById(writerId)).thenReturn(Optional.ofNullable(null))

        // when/then
        assertThrows<UserServiceException.NotMatchSessionToUser> {
            boardService.addPost(title, content, writerId)
        }
    }

    @Test
    fun 게시글_삭제() {
        // given
        val postId = 1L
        val writerId = "id"

        val user = User(writerId, "password")
        val post = Post(user, "title", "content", postId)

        Mockito.`when`(postRepository.findByIdAndStatus(postId, Post.Status.PUBLISHED)).thenReturn(post)

        // when / then
        boardService.deletePost(postId, writerId)
    }

    @Test
    fun 게시글_삭제_실패_게시글을_찾지_못했을_때() {
        // given
        val postId = 1L
        val writerId = "id"

        Mockito.`when`(postRepository.findByIdAndStatus(postId, Post.Status.PUBLISHED)).thenReturn(null)

        // when / then
        assertThrows<BoardServiceException.NotFoundPost> {
            boardService.deletePost(postId, writerId)
        }
    }

    @Test
    fun 게시글_삭제_게시글_작성한_유저가_아닐_때() {
        // given
        val postId = 1L
        val sessionUserId = "id"

        val writerUser = User("id2", "password")
        val post = Post(writerUser, "title", "content", postId)

        Mockito.`when`(postRepository.findByIdAndStatus(postId, Post.Status.PUBLISHED)).thenReturn(post)

        // when / then
        assertThrows<BoardServiceException.NotWriterOfPost> {
            boardService.deletePost(postId, sessionUserId)
        }
    }


}