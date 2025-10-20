package org.example.model

import jakarta.persistence.*

@Entity
@Table(name="comments")
data class Comment(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    //default degerini null veriyoruz boylece comment nesnesi yaratirken hic id kullanmamıza gerek kalmıyor

    @Column(nullable = false)
    val comment: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    val post: Post

)

//hatalı modelleme versiyonu
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
//    val user: User

//Fark nerede?
//Bizim istediğimiz senaryo:
//User bir Post yazar → Başka kullanıcılar Post’a yorum yapar.
//Yorum doğrudan User’a değil, Post’a bağlıdır.

//Senin senaryon:
//Yorumlar sadece User’a bağlı.
//O zaman hangi Post’a yazıldığı belli değil → hatalı modelleme.

//User (1) → Post (n)
//Post (1) → Comment (n)