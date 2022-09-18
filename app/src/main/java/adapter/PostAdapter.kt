package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostCardBinding
import java.math.RoundingMode
import java.text.DecimalFormat


interface OnInteractionListener {
    fun onEdit(post: Post) {}
    fun onLike(post: Post) {}
    fun onShare(post: Post) {}
    fun onRemove(post: Post) {}
}

class PostsAdapter(private val OnInteractionListener: OnInteractionListener
)
    : ListAdapter<Post, PostViewHolder>(PostDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = PostCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, OnInteractionListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }

}

class PostDiffCallback : DiffUtil.ItemCallback<Post>(){
    override fun areItemsTheSame(oldItem: Post,newItem: Post) : Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post,newItem: Post) : Boolean {
        return oldItem == newItem
    }
}

class PostViewHolder(
    private val binding: PostCardBinding,
    private val OnInteractionListener: OnInteractionListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            like.setImageResource(
                if (post.likedByMe) R.drawable.like_button else R.drawable.dislike
            )
            countLike.text = displayNumbers(post.likes)
            countRepost.text = displayNumbers(post.reposts)

            repost.setImageResource(
                if (post.reposts > 0) R.drawable.share_yes else R.drawable.share_non
            )

            like.setOnClickListener{
                OnInteractionListener.onLike(post)
            }

            repost.setOnClickListener{
                OnInteractionListener.onShare(post)
            }

            menu.setOnClickListener{
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post)  // Пункты меню(ресурс)
                    setOnMenuItemClickListener { item ->        //Обработчик клика
                        when (item.itemId) {
                            R.id.remove -> {
                                OnInteractionListener.onRemove(post)
                                true
                            }
                            R.id.edit -> {
                                OnInteractionListener.onEdit(post)
                                true
                            }
                            else -> false
                        }
                    }

                }.show() // показ меню
            }
        }
    }

    fun displayNumbers(number: Long): String {
        val decimalFormat = DecimalFormat("#.#")
        decimalFormat.roundingMode = RoundingMode.DOWN
        return when (number) {
            in 0..999 -> "$number"
            in 1000..9_999 -> "${decimalFormat.format(number.toFloat() / 1_000)}K"
            in 10_000..999_999 -> "${number / 1_000}K"
            in 1_000_000..9_999_999 -> "${decimalFormat.format(number.toFloat() / 1_000_000)}M"
            else -> "${number / 1_000_000}M"
        }
    }

}