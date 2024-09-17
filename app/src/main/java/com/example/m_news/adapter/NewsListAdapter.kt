package com.example.m_news.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.m_news.R
import com.example.m_news.data.model.News
import com.example.m_news.databinding.NewsViewPlaceholderBinding
import com.squareup.picasso.Picasso

class NewsListAdapter(
    private val isBookmark: Boolean,
    private val onItemClicked: (News) -> Unit,
    private val onBookmarkClick: (News) -> Unit
) : ListAdapter<News, NewsListAdapter.NewsListViewHolder>(DiffCallback) {

    inner class NewsListViewHolder(private val binding: NewsViewPlaceholderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(news: News) {
            binding.newsTitle.text = news.title ?: "Title"
            binding.newsDescription.text = news.description ?: "Description"
            binding.sourceName.text = news.source?.name ?: "Source name"
            val imageUrl = news.urlToImage

            if (imageUrl != null) {
                Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.drawable.broken_image)
                    .error(R.drawable.broken_image)
                    .into(binding.newsImage)
            } else {
                binding.newsImage.setImageResource(R.drawable.broken_image)
            }

            if (isBookmark) {
                binding.bookmarkButton.visibility = View.GONE
            } else {
                binding.bookmarkButton.apply {
                    this.setImageResource(
                        if (news.isBookmarked) R.drawable.bookmark_checked else R.drawable.bookmark_outline
                    )
                    this.setOnClickListener {
                        onBookmarkClick(news)
                    }
                }
            }

            itemView.setOnClickListener {
                onItemClicked(news)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListViewHolder {
        return NewsListViewHolder(
            NewsViewPlaceholderBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsListViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<News>() {
            override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
                return oldItem.url.hashCode().toString() == newItem.url.hashCode().toString()
            }

            override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
                return oldItem == newItem
            }
        }
    }
}
