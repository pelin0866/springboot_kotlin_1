package org.example.model

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(
    name= "likes",
    uniqueConstraints = [UniqueConstraint(columnNames = ["user_id", "post_id"])]
)
data class Like(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    //bir like bir kullanıcıya aittir
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    //bir like bir posta aittir
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    val post: Post,

    //beğeninin zamanı
    @Column(nullable = false)
    val createdAt: Instant = Instant.now()

)
