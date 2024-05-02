package com.fhdufhdu.windows7board.entity

import com.fhdufhdu.windows7board.common.entity.CommonEnumConverter
import jakarta.persistence.*
import java.sql.Timestamp

@Entity
@Table(name = "post")
class Post(user: User, title: String, content: String, id: Long? = null) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    val user: User = user

    @Column(nullable = false, length = 256)
    var title: String = title
        protected set

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

    fun changeTitle(newTitle: String) {
        title = newTitle
    }

    fun changeContent(newContent: String){
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
        class Converter: CommonEnumConverter<Status>(Status::class)
    }
}