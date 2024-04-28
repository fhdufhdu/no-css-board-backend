package com.fhdufhdu.windows7board.entity

import jakarta.persistence.*
import java.sql.Timestamp

// all open 때문에 entity는 final이 아니게 됨.
// 그러므로 set을 제한할 필요가 있음.
@Entity
@Table(name="user")
class User(id: String, password: String) {
    @Id
    val id: String = id

    @Column(nullable = false)
    var password: String = password
        protected set

    @Column(name="created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    val createdAt: Timestamp = Timestamp(System.currentTimeMillis())

    @Column(name="updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    var updatedAt: Timestamp? = null
        protected set

    fun changePassword(newPassword: String){
        password = newPassword
    }

    fun changeUpdatedAt(timestampMillis: Long){
        updatedAt = Timestamp(timestampMillis)
    }
}