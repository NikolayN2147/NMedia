package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.ru.netology.nmedia.viewmodel.PostViewModel
import java.text.DecimalFormat


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
                likeText.text = if(post.likedByMe) displayNumbers(post.likes--) else displayNumbers(post.likes++)
                shareText.text = displayNumbers(post.reposts)
                likeButton.setImageResource(
                    if (post.likedByMe) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_outline_favorite_border_24
                )

            }

        }
        binding.likeButton.setOnClickListener {
            viewModel.like()
        }
        binding.shareButton.setOnClickListener {
            viewModel.repost()
        }




    }

    private fun displayNumbers(number: Int): String {
        val decimalFormat = DecimalFormat("#.#")
        return when (number) {
            in 0..999 -> "$number"
            in 1000..99_999 -> "${decimalFormat.format(number.toFloat() / 1_000)}K"
            in 10_000..999_999 -> "${number / 1_000}K"
            else -> "${decimalFormat.format(number.toFloat() / 1_000_000)}M"
        }
    }

}


