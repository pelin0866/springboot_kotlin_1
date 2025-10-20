package org.example.dto

import jakarta.validation.constraints.NotBlank

//clientdan(postman ya da frontend) yeni Post Yaratmak icin gelecek JSON ı temsil eder.
data class CreatePostRequest(
    @field:NotBlank val title: String,
    @field:NotBlank val content: String
)
//db den gelen post entitysini dıs dunyaya donerken kullanılır
data class PostResponse(
    val id: Long, //db nin urettigi benzersiz kimlik
    val title: String,
    val content: String
)
// UPDATE post: /posts/{postId}
data class UpdatePostRequest(val title: String, val content: String)
//CreatePostRequest → Client → Server (Request Body).
//
//PostResponse → Server → Client (Response Body).
//
//İkisini ayırmamızın nedeni:
//
//Request’te id yok (çünkü DB üretecek).
//
//Response’ta id var (DB’den geliyor).
//
//Ayrıca validation kuralları sadece request’te çalışıyor.