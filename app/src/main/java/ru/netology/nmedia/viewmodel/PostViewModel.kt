package ru.netology.nmedia.ru.netology.nmedia.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.Post
import ru.netology.nmedia.ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.ru.netology.nmedia.data.impl.InMemoryPostRepository

private val empty = Post(            //data-объект для заполнения
    id = 0,
    author = "",
    content = "",
    published = "",
    likedByMe = false,
    likes = 0,
    reposts = 0
)

class PostViewModel : ViewModel() {
    // упрощённый вариант
    private val repository: PostRepository = InMemoryPostRepository()
    val data = repository.getAll()
    val edited = MutableLiveData(empty)

    fun save() {                          //функция сохранения
        edited.value?.let {
            repository.save(it)
        }
        edited.value = empty
    }

    fun edit(post: Post) {                //функция сохранения контента
        edited.value = post
    }

    fun changeContent(content: String) {
        val text = content.trim()
        if (edited.value?.content == text) {
            return
        }
        edited.value = edited.value?.copy(content = text)
    }

    fun likeById(id: Long) = repository.likeById(id)
    fun shareById(id: Long) = repository.shareById(id)
    fun removeById(id: Long) = repository.removeById(id)
//    fun share() = repository.share()
}