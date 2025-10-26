package org.example.model

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id //primary key: id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val name:String,

    @Column(nullable = false, unique = true)
    val email: String,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    //@JoinColumn()
    val posts: MutableList<Post> = mutableListOf()

)
