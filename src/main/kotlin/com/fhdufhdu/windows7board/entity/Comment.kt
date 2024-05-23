package com.fhdufhdu.windows7board.entity

import com.fhdufhdu.windows7board.common.entity.CommonEnumConverter
import jakarta.persistence.*
import java.sql.Timestamp

@Entity
@Table(name = "comment")
class Comment(user: User, post: Post, content: String) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    val user: User = user

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id", nullable = false)
    val post: Post = post

    @Column(nullable = false, columnDefinition = "TEXT")
    var content: String = content
        protected set

    @Column
    @Convert(converter = Status.Converter::class)
    var status: Status = Status.PUBLISHED
        protected set

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    val createdAt: Timestamp = Timestamp(System.currentTimeMillis())

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    var updatedAt: Timestamp? = null
        protected set

    fun changeContent(newContent: String) {
        content = newContent
    }

    fun changeUpdatedAt(timestampMillis: Long) {
        updatedAt = Timestamp(timestampMillis)
    }

    fun changeStatus(newStatus: Status){
        status = newStatus
    }

    enum class Status{
        PUBLISHED, DELETED;

        @jakarta.persistence.Converter(autoApply = true)
        class Converter: CommonEnumConverter<Comment.Status>(Comment.Status::class)
    }
}