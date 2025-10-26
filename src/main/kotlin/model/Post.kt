package org.example.model

import jakarta.persistence.*


@Entity
@Table(name="posts")
data class Post(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false) //title can not be empty  nullable=false
    val title: String, //every comment text should have a title

    @Column(nullable=false, columnDefinition = "TEXT") //content can not be empty nullable=false
    val content: String,

    //performance-->LAZY
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    val user: User,

    @OneToMany(mappedBy = "post", cascade = [CascadeType.ALL], orphanRemoval = true)
    val comments: MutableList<Comment> = mutableListOf()
)

