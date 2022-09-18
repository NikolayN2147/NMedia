package ru.netology.nmedia

data class Post (
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likes: Long,
    var reposts: Long,
    val likedByMe: Boolean
)