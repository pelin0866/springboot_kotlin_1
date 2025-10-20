package org.example.model

import jakarta.persistence.*

//burada post bi kullanıcıya ait, User tarafından da "bu user ın postları" koleksiyonu olacak

@Entity
@Table(name="posts")
data class Post(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null, //default degerini null veriyoruz boylece post nesnesı yaratırken id hiç set etmıyoruz

    @Column(nullable = false) //title bos olamaz yani nullable=false
    val title: String, //user a ait her textin bir baslıgı olmak zorunda o da strıng omak zorunda

    @Column(nullable=false, columnDefinition = "TEXT") //content bos olamaz yani nullable=false
    val content: String, //sql tarafında da text olarak tanımlansın dedik buna suanda takılma, kabul et

    //istemeden getirmemek performans için önemli.
    @ManyToOne(fetch = FetchType.LAZY) //postu cekerken direkt user bilgisini hemen getirme, user a ihtiyac duyulursa ayrı bir quesry ile getir
    @JoinColumn(name="user_id", nullable = false)
    val user: User
)

//joincolumn ile user a baglaniyor
//one to many: post listesini tutuyor
//son satırdan sonra post tablosunun geldiği hal: Post:id, title, content, user_id