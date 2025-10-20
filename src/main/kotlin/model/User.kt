package org.example.model

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id //primary key id atadık, ısmı prımar key yapmak mantıksızdı,cunku ıkı tane ali eklenemezdı
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null, //sadece isim ve mail de versek h2 db otomatık olarak id atayacak
    //nullable Long yani null da olabilir demek

    @Column(nullable = false) //nullable=false bu alan bos olamaz demek
    val name:String,

    @Column(nullable = false, unique = true) //bu alan bos olamaz ve bu allanda tekar eden deger olamaz
    val email: String, //yine aynı mailden ikinci kez eklemeye calısırsan hata alırsın

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    //@JoinColumn()
    val posts: MutableList<Post> = mutableListOf()

)
