
package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.databinding.ActivityMainBinding
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    var countReposts = 10000
    var countLikes = 999

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netology.ru",
            published = "21 мая в 18:36",
            likedByMe = false
        )
        with(binding) {
            authorName.text = post.author
            date.text = post.published
            postText.text = post.content
            if (post.likedByMe) {
                likeButton.setImageResource(R.drawable.ic_outline_favorite_border_24)
            }
            likeButton.setOnClickListener {
                post.likedByMe = !post.likedByMe
                likeButton.setImageResource(
                    if (post.likedByMe) R.drawable.ic_outline_favorite_border_24 else R.drawable.ic_baseline_favorite_24
                )
                likeText.setText(
                    if (post.likedByMe) displayNumbers(countLikes--) else displayNumbers(countLikes++)
                )
            }
            shareButton.setOnClickListener {
                shareText.setText(displayNumbers(countReposts++))
            }
        }
    }

    fun displayNumbers(number: Int): String {
        val decimalFormat = DecimalFormat("#.#")
        return when (number) {
            in 0..999 -> "$number"
            in 1000..99_999 -> "${decimalFormat.format(number.toFloat() / 1_000)}K"
            in 10_000..999_999 -> "${number / 1_000}K"
            else -> "${decimalFormat.format(number.toFloat() / 1_000_000)}M"
        }
    }
}
