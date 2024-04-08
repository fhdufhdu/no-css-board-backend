package com.fhdufhdu.nocssboard.entity

import jakarta.persistence.*
import java.sql.Timestamp

@Entity
@Table(name = "comment")
class Comment(user: User, board: Post, content: String) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    val user: User = user

    @ManyToOne(fetch = FetchType.LAZY)
    val board: Post = board

    @Column
    var content: String = content
        protected set

    @Column(name = "created_at")
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
}