package ru.netology.nmedia

data class Post (
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likes: Int,
    var reposts: Int,
    val likedByMe: Boolean
)