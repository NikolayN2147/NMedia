package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.ru.netology.nmedia.viewmodel.PostViewModel


class MainActivity : AppCompatActivity() {


    private val viewModel = PostViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel.data.observe(this) { post ->
            with(binding) {
                authorName.text = post.author
                date.text = post.published
                postText.text = post.content
                likeButton.setImageResource(
                    if (post.likedByMe) R.drawable.ic_baseline_favorite_24 else  R.drawable.ic_outline_favorite_border_24
                )
                likeText.text = viewModel.displayLikes(post.likes)
                shareText.text = viewModel.displayReposts(post.reposts)
            }
        }
        binding.likeButton.setOnClickListener {
            viewModel.like()
        }
                binding.shareButton.setOnClickListener {
            viewModel.repost()
        }


    }

}


