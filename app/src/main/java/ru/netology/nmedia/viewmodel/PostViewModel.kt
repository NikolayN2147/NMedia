package ru.netology.nmedia.ru.netology.nmedia.viewmodel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.ru.netology.nmedia.data.impl.InMemoryPostRepository

class PostViewModel: ViewModel() {

    private val repository:PostRepository = InMemoryPostRepository()

    val data = repository.get()
    fun like() = repository.like()
    fun repost() = repository.repost()

}