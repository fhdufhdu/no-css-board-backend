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
    val user: User = user

    @Column
    var title: String = title
        protected set

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

    fun changeTitle(newTitle: String) {
        title = newTitle
    }

    fun changeContent(newContent: String){
        content = newContent
    }

    fun changeUpdatedAt(timestampMillis: Long) {
        updatedAt = Timestamp(timestampMillis)
    }

}