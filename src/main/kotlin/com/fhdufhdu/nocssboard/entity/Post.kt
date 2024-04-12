package com.fhdufhdu.nocssboard.entity

import jakarta.persistence.*
import java.sql.Timestamp

@Entity
@Table(name = "post")
class Post(user: User, title: String, content: String) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    val user: User = user

    @Column(nullable = false)
    var title: String = title
        protected set

    @Column(nullable = false)
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
        class Converter: AttributeConverter<Status, String> {
            override fun convertToDatabaseColumn(status: Status): String = status.name

            override fun convertToEntityAttribute(value: String): Status {
                return entries.first { it.name == value }
            }

        }
    }
}